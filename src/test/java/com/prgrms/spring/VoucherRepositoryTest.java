package com.prgrms.spring;

import com.prgrms.spring.domain.customer.Customer;
import com.prgrms.spring.domain.voucher.FixedAmountVoucher;
import com.prgrms.spring.domain.voucher.PercentDiscountVoucher;
import com.prgrms.spring.domain.voucher.Voucher;
import com.prgrms.spring.repository.voucher.JdbcVoucherRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VoucherRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(VoucherRepositoryTest.class);

    @Configuration
    @ComponentScan(
            basePackages = {"com.prgrms.spring"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher_system")
                    .username("root")
                    .password("0828")
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
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    Voucher newFixedAmountVoucher;
    Voucher newPercentAmountVoucher;

    @BeforeAll
    void setup() {
        newFixedAmountVoucher = FixedAmountVoucher.newInstance(UUID.randomUUID(), 30000);
        newPercentAmountVoucher = PercentDiscountVoucher.newInstance(UUID.randomUUID(), 30);
    }

    @Test
    @Order(1)
    @DisplayName("바우처를 추가할 수 있다.")
    public void testInsert() {
        try {
            jdbcVoucherRepository.insert(newFixedAmountVoucher);
            jdbcVoucherRepository.insert(newPercentAmountVoucher);
        } catch (BadSqlGrammarException e) {
            logger.error("Got BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode(), e);
        }

        var retrievedVoucher = jdbcVoucherRepository.findById(newPercentAmountVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(newFixedAmountVoucher));

        var retrievedVoucher2 = jdbcVoucherRepository.findById(newPercentAmountVoucher.getVoucherId());
        assertThat(retrievedVoucher2.isEmpty(), is(false));
        assertThat(retrievedVoucher2.get(), samePropertyValuesAs(newPercentAmountVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("전체 바우처를 조회할 수 있다.")
    public void testFindAll() {
        var vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("아이디로 바우처를 조회할 수 있다.")
    public void testFindById() {
        var voucher = jdbcVoucherRepository.findById(newPercentAmountVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(false));

        var unknown = jdbcVoucherRepository.findById(UUID.randomUUID());
        assertThat(unknown.isEmpty(), is(true));
    }
}
