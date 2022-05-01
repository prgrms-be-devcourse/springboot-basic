package org.prgrms.voucherapp.engine.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.voucherapp.engine.voucher.entity.FixedAmountVoucher;
import org.prgrms.voucherapp.engine.voucher.entity.PercentDiscountVoucher;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.global.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("db")
class JdbcVoucherRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepositoryTest.class);
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.voucherapp"}
    )
    static class Config{
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_db")
                    .username("test")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
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
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager){
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    DataSource dataSource;

    Voucher fixedAmountVoucher;

    Voucher percentVoucher;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp(){
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 3000, Util.NOW());
        percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10, Util.NOW());
        MysqldConfig mySqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "1234")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mySqlConfig)
                .addSchema("voucher_db", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {embeddedMysql.stop();}

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 추가할 수 있다.")
    public void testInsert() {
        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentVoucher);

        Optional<Voucher> resultFixVoucher = voucherRepository.findById(fixedAmountVoucher.getVoucherId());
        Optional<Voucher> resultPercentVoucher = voucherRepository.findById(percentVoucher.getVoucherId());

        assertThat(resultFixVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
        assertThat(resultPercentVoucher.get(), samePropertyValuesAs(percentVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("전체 바우처를 조회할 수 있다.")
    public void testFindAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers, hasSize(2));
    }

    @Test
    @Order(4)
    @DisplayName("바우처를 수정할 수 있다.")
    public void testNameUpdate() {
        Voucher newVoucher = new FixedAmountVoucher(percentVoucher.getVoucherId(), 3000, Util.NOW());
        voucherRepository.update(newVoucher);

        Optional<Voucher> retrievedVoucher = voucherRepository.findById(newVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(5)
    @DisplayName("바우처를 삭제할 수 있다.")
    public void testDelete() {
        voucherRepository.deleteById(percentVoucher.getVoucherId());
        voucherRepository.deleteById(fixedAmountVoucher.getVoucherId());

        List<Voucher> all = voucherRepository.findAll();
        assertThat(all, hasSize(0));
    }

}