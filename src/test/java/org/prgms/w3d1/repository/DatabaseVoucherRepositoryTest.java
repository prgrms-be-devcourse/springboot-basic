package org.prgms.w3d1.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.w3d1.model.customer.Customer;
import org.prgms.w3d1.model.voucher.FixedAmountVoucher;
import org.prgms.w3d1.model.voucher.PercentDiscountVoucher;
import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherType;
import org.prgms.w3d1.model.wallet.DatabaseVoucherWalletRepository;
import org.prgms.w3d1.model.wallet.VoucherWallet;
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
class DatabaseVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgms.w3d1.model.voucher"})
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

    Customer customer;
    VoucherWallet voucherWallet;
    Voucher fixedAmountVoucher;
    Voucher percentDiscountVoucher;

    @BeforeAll
    @Transactional
    void setUp() {
        customer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now());
        databaseCustomerRepository.insert(customer);
        voucherWallet = new VoucherWallet(UUID.randomUUID(), Collections.emptyList(), customer.getCustomerId());
        databaseVoucherWalletRepository.insert(voucherWallet.getVoucherWalletId(), customer.getCustomerId());
        fixedAmountVoucher = FixedAmountVoucher.of(UUID.randomUUID(), 50L);
        percentDiscountVoucher = PercentDiscountVoucher.of(UUID.randomUUID(), 25L);
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
    @Order(1)
    @DisplayName("Id를 통해서 바우처 조회")
    void findById() {
        databaseVoucherRepository.save(fixedAmountVoucher);

        var testVoucher = databaseVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(testVoucher.isEmpty(), is(false));
        assertThat(testVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 DBMS에 저장")
    void save() {
        databaseVoucherRepository.save(fixedAmountVoucher);

        var testVoucher = databaseVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(testVoucher.isEmpty(), is(false));
        assertThat(testVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("모든 바우처 꺼내기")
    void findAll() {
        databaseVoucherRepository.save(fixedAmountVoucher);
        databaseVoucherRepository.save(percentDiscountVoucher);

        var testVouchers = databaseVoucherRepository.findAll();

        assertThat(testVouchers, containsInAnyOrder(samePropertyValuesAs(fixedAmountVoucher), samePropertyValuesAs(percentDiscountVoucher)));
    }

    @Test
    @Order(4)
    @DisplayName("바우처 지갑 id로 바우처 조회")
    void findByVoucherWalletId() {
        // 바우처 지갑 id를 가진 바우처를 만든다
        var voucherWalletId = voucherWallet.getVoucherWalletId();
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, voucherWalletId);
        databaseVoucherRepository.save(voucher);

        // 바우처 지갑 id로 바우처를 조회하여
        var testVouchers = databaseVoucherRepository.findByVoucherWalletId(voucherWalletId);

        // 나온 결과에 voucher가 있는지 확인한다
        assertThat(testVouchers, containsInAnyOrder(voucher));
    }

    @Test
    @DisplayName("바우처 타입으로 조회")
    void findByVoucherType() {
        databaseVoucherRepository.save(fixedAmountVoucher);

        var testVoucherList = databaseVoucherRepository.findByVoucherType(VoucherType.FIXED_AMOUNT_VOUCHER);

        assertThat(testVoucherList, containsInAnyOrder(fixedAmountVoucher));
    }


    @Test
    void deleteById() {
    }
}