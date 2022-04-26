package org.prgrms.voucherapplication.repository.voucher.jdbc;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.repository.customer.jdbc.JdbcCustomerRepository;
import org.prgrms.voucherapplication.view.io.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.voucherapplication.repository.voucher.jdbc"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();

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
        public JdbcCustomerRepository jdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcCustomerRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }
    }

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-voucher_mgmt", classPathScript("schema.sql"))
                .start(); // server start
    }

    @AfterEach
    void  cleanUpDb() {
        jdbcVoucherRepository.deleteAll();
        jdbcCustomerRepository.deleteAll();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("새 바우처 추가")
    void insert() {
        SqlVoucher fixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 100, LocalDateTime.now());
        SqlVoucher percentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 20, LocalDateTime.now());

        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.insert(percentDiscountVoucher);

        List<SqlVoucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers, hasSize(2));
    }

    @Test
    @DisplayName("모든 바우처 조회")
    void findAll() {
        Customer customer1 = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "test-user2", "test-user2@gmail.com", LocalDateTime.now());

        SqlVoucher fixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 100, LocalDateTime.now());
        SqlVoucher percentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 20, LocalDateTime.now());
        SqlVoucher issuedFixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 50, customer1.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());
        SqlVoucher issuedPercentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 30, customer2.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());

        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.insert(percentDiscountVoucher);
        jdbcVoucherRepository.insert(issuedFixedAmountVoucher);
        jdbcVoucherRepository.insert(issuedPercentDiscountVoucher);

        List<SqlVoucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers, hasSize(4));
    }

    @Test
    @DisplayName("id로 바우처 조회")
    void findById() {
        SqlVoucher fixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 100, LocalDateTime.now());
        SqlVoucher percentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 20, LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.insert(percentDiscountVoucher);

        Optional<SqlVoucher> fixedAmountVoucherById = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        Optional<SqlVoucher> percentDiscountVoucherById = jdbcVoucherRepository.findById(percentDiscountVoucher.getVoucherId());

        assertThat(fixedAmountVoucherById.get(), samePropertyValuesAs(fixedAmountVoucher));
        assertThat(percentDiscountVoucherById.get(), samePropertyValuesAs(percentDiscountVoucher));
    }

    @Test
    @DisplayName("isIssued(바우처 발행 여부)로 바우처 조회: 발행된 바우처 조회")
    void findIssuedVoucherByIsIssued() {
        Customer customer1 = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "test-user2", "test-user2@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer1);
        jdbcCustomerRepository.insert(customer2);

        SqlVoucher fixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 100, LocalDateTime.now());
        SqlVoucher percentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 20, LocalDateTime.now());
        SqlVoucher issuedFixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 50, customer1.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());
        SqlVoucher issuedPercentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 30, customer2.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.insert(percentDiscountVoucher);
        jdbcVoucherRepository.insert(issuedFixedAmountVoucher);
        jdbcVoucherRepository.insert(issuedPercentDiscountVoucher);

        Optional<List<SqlVoucher>> byIsIssued = jdbcVoucherRepository.findByIsIssued(true);

        assertThat(byIsIssued.isEmpty(), is(false));
        assertThat(byIsIssued.get(), hasSize(2));
    }

    @Test
    @DisplayName("isIssued(바우처 발행 여부)로 바우처 조회: 발행되지 않은 바우처 조회")
    void findUnissuedVoucherByIsIssued() {
        Customer customer1 = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "test-user2", "test-user2@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer1);
        jdbcCustomerRepository.insert(customer2);

        SqlVoucher fixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 100, LocalDateTime.now());
        SqlVoucher percentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 20, LocalDateTime.now());
        SqlVoucher issuedFixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 50, customer1.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());
        SqlVoucher issuedPercentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 30, customer2.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.insert(percentDiscountVoucher);
        jdbcVoucherRepository.insert(issuedFixedAmountVoucher);
        jdbcVoucherRepository.insert(issuedPercentDiscountVoucher);

        Optional<List<SqlVoucher>> byIsIssued = jdbcVoucherRepository.findByIsIssued(false);

        assertThat(byIsIssued.isEmpty(), is(false));
        assertThat(byIsIssued.get(), hasSize(2));
    }

    @Test
    @DisplayName("voucherOwner(바우처 소유자)로 바우처 조회")
    void findByVoucherOwner() {
        Customer customer = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer);

        SqlVoucher fixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 100, LocalDateTime.now());
        SqlVoucher percentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 20, LocalDateTime.now());
        SqlVoucher issuedFixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 50, customer.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());
        SqlVoucher issuedPercentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 30, customer.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.insert(percentDiscountVoucher);
        jdbcVoucherRepository.insert(issuedFixedAmountVoucher);
        jdbcVoucherRepository.insert(issuedPercentDiscountVoucher);

        Optional<List<SqlVoucher>> byVoucherOwner = jdbcVoucherRepository.findByVoucherOwner(customer.getCustomerId());

        assertThat(byVoucherOwner.isEmpty(), is(false));
        assertThat(byVoucherOwner.get(), hasSize(2));
    }

    @Test
    @DisplayName("바우처 정보 변경: 고객에게 바우처 발행")
    void update() {
        Customer customer = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer);

        SqlVoucher fixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 100, LocalDateTime.now());
        SqlVoucher percentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 20, LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.insert(percentDiscountVoucher);

        fixedAmountVoucher.issueVoucher(customer.getCustomerId());
        jdbcVoucherRepository.update(fixedAmountVoucher);

        Optional<List<SqlVoucher>> byVoucherOwner = jdbcVoucherRepository.findByVoucherOwner(customer.getCustomerId());
        assertThat(byVoucherOwner.isEmpty(), is(false));
        assertThat(byVoucherOwner.get(), hasSize(1));
    }

    @Test
    @DisplayName("바우처 삭제: 특정 고객이 가진 바우처 삭제")
    void deleteById() {
        Customer customer = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer);

        SqlVoucher fixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 100, LocalDateTime.now());
        SqlVoucher percentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 20, LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.insert(percentDiscountVoucher);

        fixedAmountVoucher.issueVoucher(customer.getCustomerId());
        jdbcVoucherRepository.update(fixedAmountVoucher);

        jdbcVoucherRepository.deleteById(fixedAmountVoucher.getVoucherId());

        Optional<List<SqlVoucher>> byVoucherOwner = jdbcVoucherRepository.findByVoucherOwner(customer.getCustomerId());
        Optional<SqlVoucher> byId = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(byVoucherOwner.get(), hasSize(0));
        assertThat(byId.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 삭제: 모든 바우처 삭제")
    void deleteAll() {
        Customer customer1 = new Customer(UUID.randomUUID(), "test-user1", "test-user1@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "test-user2", "test-user2@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.insert(customer1);
        jdbcCustomerRepository.insert(customer2);

        SqlVoucher fixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 100, LocalDateTime.now());
        SqlVoucher percentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 20, LocalDateTime.now());
        SqlVoucher issuedFixedAmountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT.name(), 50, customer1.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());
        SqlVoucher issuedPercentDiscountVoucher = new SqlVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT.name(), 30, customer2.getCustomerId(), true, LocalDateTime.now(), LocalDateTime.now());
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcVoucherRepository.insert(percentDiscountVoucher);
        jdbcVoucherRepository.insert(issuedFixedAmountVoucher);
        jdbcVoucherRepository.insert(issuedPercentDiscountVoucher);

        jdbcVoucherRepository.deleteAll();

        List<SqlVoucher> all = jdbcVoucherRepository.findAll();
        assertThat(all, hasSize(0));
    }
}