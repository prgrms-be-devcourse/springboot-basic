package com.prgrms.vouchermanagement.wallet;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.customer.CustomerNamedJdbcRepository;
import com.prgrms.vouchermanagement.customer.CustomerRepository;
import com.prgrms.vouchermanagement.customer.CustomerService;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.repository.JdbcVoucherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prgrms.vouchermanagement.voucher.VoucherType.FIXED_DISCOUNT;
import static com.prgrms.vouchermanagement.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class JdbcVoucherWalletRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = "com.prgrms.vouchermanagement")
    static class TestConfig {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(EmbeddedDatabaseType.H2)
                    .addScripts("voucher_schema.sql", "customer_schema.sql", "wallet_schema.sql")
                    .setScriptEncoding("UTF-8")
                    .build();
        }

        @Bean
        NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        JdbcVoucherRepository voucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcVoucherRepository(jdbcTemplate);
        }

        @Bean
        CustomerNamedJdbcRepository customerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new CustomerNamedJdbcRepository(jdbcTemplate);
        }


        @Bean
        CustomerService customerService(CustomerRepository customerRepository) {
            return new CustomerService(customerRepository);
        }

        @Bean
        JdbcVoucherWalletRepository jdbcVoucherWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcVoucherWalletRepository(jdbcTemplate);
        }
    }

    @Autowired
    JdbcVoucherWalletRepository voucherWalletRepository;

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    CustomerNamedJdbcRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @AfterEach
    void afterEach() {
        voucherWalletRepository.clear();
        customerRepository.clear();
        voucherRepository.clear();
    }

    @Test
    @DisplayName("Customer의 Wallet에 voucher를 추가한다.")
    void saveTest() {
        // given
        Voucher voucher = FIXED_DISCOUNT.constructor(50, LocalDateTime.now());
        Customer customer = Customer.of("aaa", "aaa@gmail.com");
        Long voucherId = voucherRepository.save(voucher);
        Long customerId = customerRepository.save(customer);

        // when
        Wallet wallet = Wallet.of(customerId, voucherId);
        Long walletId = voucherWalletRepository.save(wallet);

        // then
        List<Voucher> findVoucher = voucherRepository.findVoucherByCustomer(customerId);
        assertThat(findVoucher.size()).isEqualTo(1);
        assertThat(findVoucher.get(0)).usingRecursiveComparison().ignoringFields("voucherId").isEqualTo(voucher);
    }

    @Test
    @DisplayName("Customer가 가지고 있는 Voucher를 조회힌다.")
    void findVoucherByCustomerTest() {
        // given
        Voucher voucher1 = FIXED_DISCOUNT.constructor(5000, LocalDateTime.now());
        Voucher voucher2 = PERCENT_DISCOUNT.constructor(10, LocalDateTime.now());
        Voucher voucher3 = FIXED_DISCOUNT.constructor(50000, LocalDateTime.now());
        Long voucherId1 = voucherRepository.save(voucher1);
        Long voucherId2 = voucherRepository.save(voucher2);
        Long voucherId3 = voucherRepository.save(voucher3);


        Customer customer = Customer.of("aaa", "aaa@gmail.com");
        Long customerId = customerRepository.save(customer);

        voucherWalletRepository.save(Wallet.of(customerId, voucherId1));
        voucherWalletRepository.save(Wallet.of(customerId, voucherId2));
        voucherWalletRepository.save(Wallet.of(customerId, voucherId3));

        // when
        List<Voucher> findVouchers = voucherRepository.findVoucherByCustomer(customerId);

        // then
        assertThat(findVouchers.size()).isEqualTo(3);
        assertThat(findVouchers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("voucherId").contains(voucher1, voucher2, voucher3);
    }

    @Test
    @DisplayName("Voucher를 가지고 있는 Customer를 조회한다.")
    void findCustomerByVoucherTest() {
        // given
        Customer customer1 = Customer.of("aaa", "aaa@gmail.com");
        Customer customer2 = Customer.of("bbb", "bbb@gmail.com");
        Customer customer3 = Customer.of("ccc", "ccc@gmail.com");
        Long customerId1 = customerRepository.save(customer1);
        Long customerId2 = customerRepository.save(customer2);
        Long customerId3 = customerRepository.save(customer3);

        Voucher voucher = FIXED_DISCOUNT.constructor(5000, LocalDateTime.now());
        Long voucherId = voucherRepository.save(voucher);

        voucherWalletRepository.save(Wallet.of(customerId1, voucherId));
        voucherWalletRepository.save(Wallet.of(customerId2, voucherId));
        voucherWalletRepository.save(Wallet.of(customerId3, voucherId));

        // when
        List<Customer> findCustomers = customerService.findCustomerByVoucher(voucherId);

        // then
        assertThat(findCustomers.size()).isEqualTo(3);
        assertThat(findCustomers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("customerId").contains(customer1, customer2, customer3);
    }

    @Test
    @DisplayName("Wallet을 삭제한다.")
    void removeTest() {
        // given
        Customer customer = Customer.of("aaa", "aaa@gmail.com");
        Long customerId = customerRepository.save(customer);

        Voucher voucher = FIXED_DISCOUNT.constructor(5000, LocalDateTime.now());
        Long voucherId = voucherRepository.save(voucher);

        Wallet wallet = Wallet.of(customerId, voucherId);
        Long walletId = voucherWalletRepository.save(wallet);

        // when
        voucherWalletRepository.removeWallet(walletId);

        // then
        List<Voucher> vouchers = voucherRepository.findVoucherByCustomer(customerId);
        assertThat(vouchers.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Wallet을 조회한다.")
    void findWalletTest() {
        // given
        Voucher voucher = FIXED_DISCOUNT.constructor(500, LocalDateTime.now());
        Long voucherId = voucherRepository.save(voucher);

        Customer customer = Customer.of("aaa", "aaa@gmail.com");
        Long customerId = customerRepository.save(customer);

        Wallet wallet = Wallet.of(customerId, voucherId);
        Long walletId = voucherWalletRepository.save(wallet);

        // when
        Optional<Wallet> findWallet = voucherWalletRepository.findWallet(walletId);

        // then
        assertThat(findWallet).isNotEmpty();
        assertThat(findWallet.get()).usingRecursiveComparison().ignoringFields("walletId").isEqualTo(wallet);
    }

    @Test
    @DisplayName("등록되지 않은 walletId로 Wallet을 조회하면 Optional.empty()를 반환한다.")
    void findWalletFailTest() {
        // given
        Voucher voucher = FIXED_DISCOUNT.constructor(500, LocalDateTime.now());
        Long voucherId = voucherRepository.save(voucher);

        Customer customer = Customer.of("aaa", "aaa@gmail.com");
        Long customerId = customerRepository.save(customer);

        Wallet wallet = Wallet.of(customerId, voucherId);
        voucherWalletRepository.save(wallet);

        Long wrongWalletId = -1L;

        // when
        Optional<Wallet> findWallet = voucherWalletRepository.findWallet(wrongWalletId);

        // then
        assertThat(findWallet).isEmpty();
    }
}