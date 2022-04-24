package com.prgrms.vouchermanagement.wallet;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.customer.CustomerNamedJdbcRepository;
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
import java.util.UUID;

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
        Voucher voucher = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 50, LocalDateTime.now());
        Customer customer = Customer.of(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        voucherRepository.save(voucher);
        customerRepository.save(customer);

        // when
        Wallet wallet = Wallet.of(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());
        voucherWalletRepository.save(wallet);

        // then
        List<Voucher> findVoucher = voucherWalletRepository.findVoucherByCustomer(customer.getCustomerId());
        assertThat(findVoucher.size()).isEqualTo(1);
        assertThat(findVoucher).containsExactly(voucher);
    }

    @Test
    @DisplayName("Customer가 가지고 있는 Voucher를 조회힌다.")
    void findVoucherByCustomerTest() {
        // given
        Voucher voucher1 = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 5000, LocalDateTime.now());
        Voucher voucher2 = PERCENT_DISCOUNT.constructor(UUID.randomUUID(), 10, LocalDateTime.now());
        Voucher voucher3 = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 50000, LocalDateTime.now());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);


        Customer customer = Customer.of(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        voucherWalletRepository.save(Wallet.of(UUID.randomUUID(), customer.getCustomerId(), voucher1.getVoucherId()));
        voucherWalletRepository.save(Wallet.of(UUID.randomUUID(), customer.getCustomerId(), voucher2.getVoucherId()));
        voucherWalletRepository.save(Wallet.of(UUID.randomUUID(), customer.getCustomerId(), voucher3.getVoucherId()));

        // when
        List<Voucher> findVouchers = voucherWalletRepository.findVoucherByCustomer(customer.getCustomerId());

        // then
        assertThat(findVouchers.size()).isEqualTo(3);
        assertThat(findVouchers).contains(voucher1, voucher2, voucher3);
    }

    @Test
    @DisplayName("Voucher를 가지고 있는 Customer를 조회한다.")
    void findCustomerByVoucherTest() {
        // given
        Customer customer1 = Customer.of(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        Customer customer2 = Customer.of(UUID.randomUUID(), "bbb", "bbb@gmail.com", LocalDateTime.now());
        Customer customer3 = Customer.of(UUID.randomUUID(), "ccc", "ccc@gmail.com", LocalDateTime.now());
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        Voucher voucher = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 5000, LocalDateTime.now());
        voucherRepository.save(voucher);

        voucherWalletRepository.save(Wallet.of(UUID.randomUUID(), customer1.getCustomerId(), voucher.getVoucherId()));
        voucherWalletRepository.save(Wallet.of(UUID.randomUUID(), customer2.getCustomerId(), voucher.getVoucherId()));
        voucherWalletRepository.save(Wallet.of(UUID.randomUUID(), customer3.getCustomerId(), voucher.getVoucherId()));

        // when
        List<Customer> findCustomers = voucherWalletRepository.findCustomerByVoucher(voucher.getVoucherId());

        // then
        assertThat(findCustomers.size()).isEqualTo(3);
        assertThat(findCustomers).usingRecursiveFieldByFieldElementComparator().contains(customer1, customer2, customer3);
    }

    @Test
    @DisplayName("Wallet을 삭제한다.")
    void removeTest() {
        // given
        Customer customer = Customer.of(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        Voucher voucher = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 5000, LocalDateTime.now());
        voucherRepository.save(voucher);

        Wallet wallet = Wallet.of(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());
        voucherWalletRepository.save(wallet);

        // when
        voucherWalletRepository.removeWallet(wallet.getWalletId());

        // then
        List<Voucher> vouchers = voucherWalletRepository.findVoucherByCustomer(customer.getCustomerId());
        assertThat(vouchers.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Wallet을 조회한다.")
    void findWalletTest() {
        // given
        Voucher voucher = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 500, LocalDateTime.now());
        voucherRepository.save(voucher);

        Customer customer = Customer.of(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        Wallet wallet = Wallet.of(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());
        voucherWalletRepository.save(wallet);

        // when
        Optional<Wallet> findWallet = voucherWalletRepository.findWallet(wallet.getWalletId());

        // then
        assertThat(findWallet).isNotEmpty();
        assertThat(findWallet.get()).usingRecursiveComparison().isEqualTo(wallet);
    }

    @Test
    @DisplayName("등록되지 않은 walletId로 Wallet을 조회하면 Optional.empty()를 반환한다.")
    void findWalletFailTest() {
        // given
        Voucher voucher1 = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 500, LocalDateTime.now());
        voucherRepository.save(voucher1);

        Voucher voucher2 = FIXED_DISCOUNT.constructor(UUID.randomUUID(), 100000, LocalDateTime.now());
        voucherRepository.save(voucher2);

        Customer customer = Customer.of(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        Wallet wallet = Wallet.of(UUID.randomUUID(), customer.getCustomerId(), voucher1.getVoucherId());
        voucherWalletRepository.save(wallet);

        // when
        Optional<Wallet> findWallet = voucherWalletRepository.findWallet(UUID.randomUUID());

        // then
        assertThat(findWallet).isEmpty();
    }
}