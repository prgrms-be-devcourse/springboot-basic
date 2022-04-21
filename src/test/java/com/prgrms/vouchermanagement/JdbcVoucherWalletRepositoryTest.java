package com.prgrms.vouchermanagement;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.customer.CustomerNamedJdbcRepository;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.repository.JdbcVoucherRepository;
import com.prgrms.vouchermanagement.wallet.JdbcVoucherWalletRepository;
import com.prgrms.vouchermanagement.wallet.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class JdbcVoucherWalletRepositoryTest {

    @Configuration
    @ComponentScan
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
        Voucher voucher = VoucherType.FIXED_DISCOUNT.constructor(UUID.randomUUID(), 50, LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        voucherRepository.save(voucher);
        customerRepository.save(customer);

        // when
        Wallet wallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());
        voucherWalletRepository.save(wallet);

        // then
        List<Voucher> findVoucher = voucherWalletRepository.findVoucherByCustomer(customer);
        assertThat(findVoucher.size()).isEqualTo(1);
        assertThat(findVoucher).containsExactly(voucher);
    }

    @Test
    @DisplayName("Customer가 가지고 있는 Voucher를 조회힌다.")
    void findVoucherByCustomerTest() {
        // given
        Voucher voucher1 = VoucherType.FIXED_DISCOUNT.constructor(UUID.randomUUID(), 5000, LocalDateTime.now());
        Voucher voucher2 = VoucherType.PERCENT_DISCOUNT.constructor(UUID.randomUUID(), 10, LocalDateTime.now());
        Voucher voucher3 = VoucherType.FIXED_DISCOUNT.constructor(UUID.randomUUID(), 50000, LocalDateTime.now());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);


        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        voucherWalletRepository.save(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher1.getVoucherId()));
        voucherWalletRepository.save(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher2.getVoucherId()));
        voucherWalletRepository.save(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher3.getVoucherId()));

        // when
        List<Voucher> findVouchers = voucherWalletRepository.findVoucherByCustomer(customer);

        // then
        assertThat(findVouchers.size()).isEqualTo(3);
        assertThat(findVouchers).contains(voucher1, voucher2, voucher3);
    }

    @Test
    @DisplayName("Voucher를 가지고 있는 Customer를 조회한다.")
    void findCustomerByVcouehrTest() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "bbb", "bbb@gmail.com", LocalDateTime.now());
        Customer customer3 = new Customer(UUID.randomUUID(), "ccc", "ccc@gmail.com", LocalDateTime.now());
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        Voucher voucher = VoucherType.FIXED_DISCOUNT.constructor(UUID.randomUUID(), 5000, LocalDateTime.now());
        voucherRepository.save(voucher);

        voucherWalletRepository.save(new Wallet(UUID.randomUUID(), customer1.getCustomerId(), voucher.getVoucherId()));
        voucherWalletRepository.save(new Wallet(UUID.randomUUID(), customer2.getCustomerId(), voucher.getVoucherId()));
        voucherWalletRepository.save(new Wallet(UUID.randomUUID(), customer3.getCustomerId(), voucher.getVoucherId()));

        // when
        List<Customer> findCustomers = voucherWalletRepository.findCustomerByVoucher(voucher);

        // then
        assertThat(findCustomers.size()).isEqualTo(3);
        assertThat(findCustomers).usingRecursiveFieldByFieldElementComparator().contains(customer1, customer2, customer3);
    }

    @Test
    @DisplayName("Wallet을 삭제한다.")
    void removeTest() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);

        Voucher voucher = VoucherType.FIXED_DISCOUNT.constructor(UUID.randomUUID(), 5000, LocalDateTime.now());
        voucherRepository.save(voucher);

        Wallet wallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());
        voucherWalletRepository.save(wallet);

        // when
        voucherWalletRepository.remove(wallet.getWalletId());

        // then
        List<Voucher> vouchers = voucherWalletRepository.findVoucherByCustomer(customer);
        assertThat(vouchers.size()).isEqualTo(0);
    }
}