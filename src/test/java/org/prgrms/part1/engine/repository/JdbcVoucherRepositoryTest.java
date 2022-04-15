package org.prgrms.part1.engine.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.engine.domain.FixedAmountVoucher;
import org.prgrms.part1.engine.domain.PercentDiscountVoucher;
import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.enumtype.VoucherType;
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

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {
    @Configuration
    @ComponentScan()
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("1q2w3e4r!")
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
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
            return new TransactionTemplate(transactionManager);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcVoucherRepository voucherRepository;

    Voucher newFixedAmountVoucher;
    Voucher newPercentDiscountVoucher;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        newFixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 5000, LocalDateTime.now().withNano(0));
        newPercentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, LocalDateTime.now().withNano(0));
        MysqldConfig config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "1q2w3e4r!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 추가할 수 있다.")
    public void testInsert() {
        voucherRepository.insert(newFixedAmountVoucher);
        voucherRepository.insert(newPercentDiscountVoucher);

        Optional<Voucher> retrievedFixedAmountVoucher = voucherRepository.findById(newFixedAmountVoucher.getVoucherId());
        Optional<Voucher> retrievedPercentDiscountVoucherVoucher = voucherRepository.findById(newPercentDiscountVoucher.getVoucherId());

        assertThat(retrievedFixedAmountVoucher.isEmpty(), is(false));
        assertThat(retrievedFixedAmountVoucher.get(), samePropertyValuesAs(newFixedAmountVoucher));

        assertThat(retrievedPercentDiscountVoucherVoucher.isEmpty(), is(false));
        assertThat(retrievedPercentDiscountVoucherVoucher.get(), samePropertyValuesAs(newPercentDiscountVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("전체 바우처를 조회할 수 있다.")
    public void testFindAll() {
        int count = voucherRepository.count();
        assertThat(count, is(2));
    }

    @Test
    @Order(4)
    @DisplayName("타입 별 바우처를 조회할 수 있다.")
    public void testFindByType() {
        var fixedAmountVouchers = voucherRepository.findByType(VoucherType.FixedAmount);
        assertThat(fixedAmountVouchers.size(), is(1));
        var percentDiscountVouchers = voucherRepository.findByType(VoucherType.PercentDiscount);
        assertThat(percentDiscountVouchers.size(), is(1));
    }

    @Test
    @Order(5)
    @DisplayName("바우처를 수정할 수 있다.")
    public void testUpdate() {
        newFixedAmountVoucher.changeValue(8000);
        voucherRepository.update(newFixedAmountVoucher);
        var changedFixedAmountVoucher = voucherRepository.findById(newFixedAmountVoucher.getVoucherId());
        assertThat(changedFixedAmountVoucher.isEmpty(), is(false));
        assertThat(changedFixedAmountVoucher.get().getValue(), is(newFixedAmountVoucher.getValue()));

        newPercentDiscountVoucher.changeValue(80);
        voucherRepository.update(newPercentDiscountVoucher);
        var changedPercentDiscountVoucher = voucherRepository.findById(newPercentDiscountVoucher.getVoucherId());
        assertThat(changedPercentDiscountVoucher.isEmpty(), is(false));
        assertThat(changedPercentDiscountVoucher.get().getValue(), is(newPercentDiscountVoucher.getValue()));
    }
}