package org.prgrms.kdt.domain.voucher;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.customer.CustomerNamedJdbcRepository;
import org.prgrms.kdt.domain.customer.Email;
import org.prgrms.kdt.domain.customer.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Slf4j
@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherNamedJdbcRepositoryTest {

    @Autowired
    VoucherNamedJdbcRepository voucherNamedJdbcRepository;

    @Autowired
    CustomerNamedJdbcRepository customerNamedJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;
    Voucher newVoucher;

    static Stream<Arguments> voucherSource() {
        return Stream.of(arguments(Lists.list(
                new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000),
                        VoucherType.FIXED_AMOUNT,
                        200),
                new PercentDiscountVoucher(new RandomDataGenerator().nextLong(0, 10000),
                        VoucherType.PERCENT_DISCOUNT,
                        10)
                )
        ));
    }

    @BeforeAll
    void setup() {
        newCustomer = new Customer(new RandomDataGenerator().nextLong(0, 10000),
                Name.valueOf("test"),
                Email.valueOf("test@gmail.com"),
                LocalDateTime.now(),
                LocalDateTime.now());
        customerNamedJdbcRepository.insert(newCustomer);

        newVoucher = new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000),
                VoucherType.FIXED_AMOUNT,
                100);
        voucherNamedJdbcRepository.insert(newVoucher);
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @ParameterizedTest
    @Order(2)
    @DisplayName("바우처를 추가할 수 있다.")
    @MethodSource("voucherSource")
    public void testInsert(List<Voucher> vouchers) {
        for (Voucher voucher : vouchers) {
            try {
                voucherNamedJdbcRepository.insert(voucher);
            } catch (BadSqlGrammarException e) {
                log.error("Got BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode(), e);
            }
        }
        assertThat(voucherNamedJdbcRepository.findAll().isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("전체 바우처를 조회할 수 있다.")
    public void testFindAll() {
        var vouchers = voucherNamedJdbcRepository.findAll();
        for (Voucher voucher : vouchers) {
            log.info("[*] every Voucher: {}", voucher);
        }
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("바우처를 고객에 부여할 수 있다.")
    public void testAllocate() {
        voucherNamedJdbcRepository.update(newVoucher, newCustomer);
        var allocatedVoucher = voucherNamedJdbcRepository.findById(newVoucher.getVoucherId()).orElseThrow();
        log.info("[*] allocatedVoucher: {}", allocatedVoucher);
        assertThat(allocatedVoucher.getCustomerId(), is(newCustomer.getCustomerId()));
    }

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.domain",
                    "org.prgrms.kdt.common",
                    "org.prgrms.kdt.service"
            }
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt?characterEncoding=utf8&serverTimezone=UTC")
                    .username("kdt")
                    .password("kdt")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
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

}
