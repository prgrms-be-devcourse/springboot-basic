package org.prgms.w3d1.model.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.w3d1.model.voucher.FixedAmountVoucher;
import org.prgms.w3d1.model.voucher.PercentDiscountVoucher;
import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherType;
import org.prgms.w3d1.repository.DatabaseVoucherWalletRepository;
import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.prgms.w3d1.repository.VoucherWalletRepository;
import org.prgms.w3d1.repository.CustomerJdbcRepository;
import org.prgms.w3d1.repository.CustomerRepository;
import org.prgms.w3d1.repository.DatabaseVoucherRepository;
import org.prgms.w3d1.repository.VoucherRepository;
import org.prgms.w3d1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgms.w3d1.model.customer"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create().url("jdbc:mysql://localhost/my_order_mgmt")
                .username("root")
                .password("1111")
                .type(HikariDataSource.class)
                .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public DatabaseVoucherRepository databaseVoucherRepository(JdbcTemplate jdbcTemplate) {
            return new DatabaseVoucherRepository(jdbcTemplate);
        }

        @Bean
        CustomerJdbcRepository databaseCustomerRepository(JdbcTemplate jdbcTemplate) {
            return new CustomerJdbcRepository(jdbcTemplate);
        }

        @Bean
        public DatabaseVoucherWalletRepository databaseVoucherWalletRepository(
            JdbcTemplate jdbcTemplate, VoucherRepository voucherRepository)
        {
            return new DatabaseVoucherWalletRepository(jdbcTemplate, voucherRepository);
        }

        @Bean
        public CustomerService customerService(
            CustomerRepository customerRepository,
            VoucherRepository voucherRepository,
            VoucherWalletRepository voucherWalletRepository) {
            return new CustomerService(customerRepository, voucherRepository, voucherWalletRepository);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DatabaseVoucherRepository databaseVoucherRepository;

    @Autowired
    CustomerJdbcRepository databaseCustomerRepository;

    @Autowired
    DatabaseVoucherWalletRepository databaseVoucherWalletRepository;

    @Autowired
    CustomerService customerService;

    Customer customer;
    VoucherWallet voucherWallet;
    Voucher fixedAmountVoucher;
    Voucher percentDiscountVoucher;

    @BeforeAll
    @Transactional
    void setUp() {
        customer = new Customer(UUID.randomUUID(), "test-user", "test-user4@gmail.com", LocalDateTime.now());
        databaseCustomerRepository.insert(customer);
        voucherWallet = new VoucherWallet(UUID.randomUUID(), Collections.emptyList(), customer.getCustomerId());
        databaseVoucherWalletRepository.insert(voucherWallet.getVoucherWalletId(), customer.getCustomerId());
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, voucherWallet.getVoucherWalletId());
        percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 25L, voucherWallet.getVoucherWalletId());
        databaseVoucherRepository.save(fixedAmountVoucher);
        databaseVoucherRepository.save(percentDiscountVoucher);
    }

    @AfterAll
    void cleanUp() {
        databaseVoucherWalletRepository.deleteAll();
        databaseCustomerRepository.deleteAll();
    }

    @AfterEach
    void methodCleanUp() {
        databaseVoucherRepository.deleteAll();
    }

    @Test
    void findByVoucherType() {
        var testCustomerList = customerService.findByVoucherType(VoucherType.FIXED_AMOUNT_VOUCHER);

        assertThat(testCustomerList, hasItem(customer));
    }
}