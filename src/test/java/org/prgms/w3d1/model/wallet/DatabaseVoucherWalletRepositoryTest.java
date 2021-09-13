package org.prgms.w3d1.model.wallet;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.w3d1.model.customer.Customer;
import org.prgms.w3d1.model.voucher.FixedAmountVoucher;
import org.prgms.w3d1.repository.CustomerJdbcRepository;
import org.prgms.w3d1.repository.DatabaseVoucherRepository;
import org.prgms.w3d1.repository.DatabaseVoucherWalletRepository;
import org.prgms.w3d1.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseVoucherWalletRepositoryTest {

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
        DatabaseVoucherRepository databaseVoucherRepository(JdbcTemplate jdbcTemplate) {
            return new DatabaseVoucherRepository(jdbcTemplate);
        }

        @Bean
        public DatabaseVoucherWalletRepository databaseVoucherWalletRepository(JdbcTemplate jdbcTemplate) {
            return new DatabaseVoucherWalletRepository(jdbcTemplate);
        }

        @Bean
        CustomerJdbcRepository databaseCustomerRepository(JdbcTemplate jdbcTemplate) {
            return new CustomerJdbcRepository(jdbcTemplate);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DatabaseVoucherWalletRepository databaseVoucherWalletRepository;

    @Autowired
    CustomerJdbcRepository databaseCustomerRepository;

    @Autowired
    DatabaseVoucherRepository databaseVoucherRepository;

    UUID customerId;

    @BeforeEach
    void setupMethod() {
        customerId = UUID.randomUUID();
        databaseCustomerRepository.insert(
            new Customer(customerId, "testForVoucherWallet", "testForVoucherWallet@gmail.com", LocalDateTime.now()));
    }

    @AfterEach
    void cleanUpMethod() {
        databaseVoucherRepository.deleteAll();
        databaseVoucherWalletRepository.deleteAll();
        databaseCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("VoucherWallet을 추가할 수 있어야한다.")
    @Order(1)
    void insert() {
        // wallet id와, wallet에 들어갈 고객을 만든 뒤
        var voucherWallet  = new VoucherWallet(UUID.randomUUID(), customerId);
        databaseVoucherWalletRepository.insert(voucherWallet);

        // wallet id로 db에서 wallet을 찾아
        var testVoucherWallet = databaseVoucherWalletRepository.findById(voucherWallet.getVoucherWalletId());

        // 2개의 아이디를 비교한다
        assertThat(testVoucherWallet.get().equals(voucherWallet), is(true));
    }

    @Test
    @DisplayName("id로 VoucherWallet을 찾을 수 있어야한다.")
    @Order(2)
    void findById() {
        // wallet id를 만들어 db에 wallet을 넣은뒤
        var voucherWallet  = new VoucherWallet(UUID.randomUUID(), customerId);
        databaseVoucherWalletRepository.insert(voucherWallet);

        // 해당 wallet id를 가진 voucher를 등록하여
        var voucherId = UUID.randomUUID();
        var testVoucher = FixedAmountVoucher.of(voucherId, 100L, voucherWallet.getVoucherWalletId());
        databaseVoucherRepository.save(testVoucher);

        // wallet id로 db에서 wallet을 찾아
        var testVoucherWallet = databaseVoucherWalletRepository.findById(voucherWallet.getVoucherWalletId());

        // 비교한다
        assertThat(testVoucherWallet.get().equals(voucherWallet), is(true));
    }

    @Test
    @DisplayName("customer id로 Voucherwallet을 찾을 수 있어야한다.")
    void findByCustomerId() {
        // wallet id를 만들어 db에 wallet을 넣은뒤
        var voucherWallet  = new VoucherWallet(UUID.randomUUID(), customerId);
        databaseVoucherWalletRepository.insert(voucherWallet);

        // 해당 wallet id를 가진 voucher를 등록하여
        var voucherId = UUID.randomUUID();
        var testVoucher = FixedAmountVoucher.of(voucherId, 100L, voucherWallet.getVoucherWalletId());
        databaseVoucherRepository.save(testVoucher);

        // customer id로 db에서 wallet을 찾아
        var testVoucherWallet = databaseVoucherWalletRepository.findByCustomerId(customerId);

        // 비교한다
        assertThat(testVoucherWallet.get().equals(voucherWallet), is(true));
    }
}