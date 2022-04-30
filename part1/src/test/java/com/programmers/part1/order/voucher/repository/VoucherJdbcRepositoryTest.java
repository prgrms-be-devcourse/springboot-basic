package com.programmers.part1.order.voucher.repository;

import com.programmers.part1.domain.voucher.FixedAmountVoucher;
import com.programmers.part1.domain.voucher.PercentAmountVoucher;
import com.programmers.part1.domain.voucher.Voucher;
import com.programmers.part1.domain.voucher.VoucherType;
import com.programmers.part1.exception.NoUpdateException;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.part1.order.voucher"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher_management")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }


    }

    @Autowired
    DataSource dataSource;

    @Autowired
    VoucherJdbcRepository voucherRepository;

    EmbeddedMysql embeddedMysql;

    FixedAmountVoucher baseFixedVoucher;
    PercentAmountVoucher basePercentVoucher;

    @BeforeAll
    void setup() {

        baseFixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now());
        basePercentVoucher = new PercentAmountVoucher(UUID.randomUUID(), 20, LocalDateTime.now());

        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_management", classPathScript("schema.sql"))
                .start();

    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("바우처를 저장 테스트")
    void testSave() {
        voucherRepository.save(baseFixedVoucher);

        Optional<Voucher> maybeFixedVoucher = voucherRepository.findById(baseFixedVoucher.getVoucherId());
        assertAll("고정 바우처 저장",
                () -> assertThat(maybeFixedVoucher.isEmpty(), is(false)),
                () -> assertThat(maybeFixedVoucher.get(), samePropertyValuesAs(baseFixedVoucher))
        );
    }

    @Test
    @DisplayName("전체 바우처 조회 테스트")
    void testAll() {
        assertThat(voucherRepository.findAll().isEmpty(), is(true));

        voucherRepository.save(baseFixedVoucher);

        assertAll("1개 넣고 조회 테스트",
                () -> assertThat(voucherRepository.findAll().isEmpty(),is( false)),
                () -> assertThat(voucherRepository.findAll().size(), is(1))
        );

        voucherRepository.save(basePercentVoucher);

        assertThat(voucherRepository.findAll().size(),is(2));
    }

    @Test
    @DisplayName("바우처 수정 테스트")
    void testUpdate(){
        voucherRepository.save(baseFixedVoucher);

        Voucher updatePercentVoucher = new PercentAmountVoucher(baseFixedVoucher.getVoucherId(),30, baseFixedVoucher.getCreatedAt());
        voucherRepository.update(updatePercentVoucher);

        Optional<Voucher> maybePercentVoucher = voucherRepository.findById(updatePercentVoucher.getVoucherId());
        Voucher percentVoucher = maybePercentVoucher.orElseThrow(() -> new RuntimeException());

        assertThat(percentVoucher,samePropertyValuesAs(updatePercentVoucher));
    }

    @Test
    @DisplayName("특정 바우처 삭제 테스트")
    void testDelete(){

        voucherRepository.save(baseFixedVoucher);
        voucherRepository.save(basePercentVoucher);

        assertThrows(NoUpdateException.class,() -> voucherRepository.deleteById(UUID.randomUUID()));

        assertThat(voucherRepository.findAll().size(),is(2));

        voucherRepository.deleteById(baseFixedVoucher.getVoucherId());
        assertThat(voucherRepository.findAll().size(),is(1));

        voucherRepository.deleteById(basePercentVoucher.getVoucherId());
        assertThat(voucherRepository.findAll().size(),is(0));

    }

    @Test
    @DisplayName("카테고리 조건으로 바우처 리스트 찾기")
    void categoryAll(){

        voucherRepository.save(baseFixedVoucher);
        voucherRepository.save(new PercentAmountVoucher(UUID.randomUUID(),10, LocalDateTime.now()));
        voucherRepository.save(basePercentVoucher);
        voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(),2000, LocalDateTime.now()));

        assertThat(voucherRepository.findVouchersByVoucherType(VoucherType.FIXED).size(),is(2));
        assertThat(voucherRepository.findVouchersByVoucherType(VoucherType.PERCENT).size(),is(2));
    }
}