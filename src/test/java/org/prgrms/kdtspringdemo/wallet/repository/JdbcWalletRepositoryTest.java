package org.prgrms.kdtspringdemo.wallet.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.customer.CustomerFunction;
import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.prgrms.kdtspringdemo.customer.repository.JdbcCustomerRepository;
import org.prgrms.kdtspringdemo.voucher.domain.FixedDiscountPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.repository.JdbcVoucherRepository;
import org.prgrms.kdtspringdemo.voucher.repository.VoucherRepository;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("DB")
class JdbcWalletRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = "org.prgrms.kdtspringdemo")
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/kdt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    JdbcWalletRepository jdbcWalletRepository;
    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;
    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    private final Logger logger = LoggerFactory.getLogger(JdbcWalletRepositoryTest.class);
    Customer insertCustomer;
    Voucher insertVoucher;

    @BeforeEach
    void init() {
        jdbcWalletRepository.deleteAll();
        jdbcCustomerRepository.deleteAll();
        Customer customer = new Customer(UUID.randomUUID(), "tester01", true);
        jdbcCustomerRepository.insert(customer);
        insertCustomer = customer;
        Voucher voucher = new Voucher(UUID.randomUUID(), new FixedDiscountPolicy(1000L));
        jdbcVoucherRepository.insert(voucher);
        insertVoucher = voucher;
    }

    @Test
    @DisplayName("지갑을 추가합니다.")
    void insert() {
        //given
        Wallet wallet = new Wallet(UUID.randomUUID(), insertCustomer.getCustomerId());

        //when
        Wallet insertWallet = jdbcWalletRepository.insert(wallet);

        //then
        assertThat(wallet.getWalletId(), is(insertWallet.getWalletId()));
    }

    @Test
    @DisplayName("walletId로 지갑을 검색합니다.")
    void findWalletByWalletId() {
        //given
        Wallet wallet = jdbcWalletRepository.insert(new Wallet(UUID.randomUUID(), insertCustomer.getCustomerId()));

        //when
        Wallet findWallet = jdbcWalletRepository.findById(wallet.getWalletId()).get();

        //then
        assertThat(findWallet.getCustomerId(), is(wallet.getCustomerId()));
    }

    @Test
    @DisplayName("customerId로 voucherId 목록을 조회합니다.")
    void findVouchersByCustomerId() {
        //given
        List<UUID> vouchers = new ArrayList<>();
        vouchers.add(UUID.randomUUID());
        jdbcWalletRepository.insert(new Wallet(UUID.randomUUID(), insertCustomer.getCustomerId()));

        //when
        List<Voucher> voucherList = jdbcWalletRepository.findVouchersByCustomerId(insertCustomer.getCustomerId());

        //then
        assertThat(voucherList, samePropertyValuesAs(vouchers));
    }

    @Test
    @DisplayName("wallet안의 voucherId")
    void deleteVoucherByVoucherId() {
        //given
        UUID voucherId = UUID.randomUUID();
        Wallet insertWallet = jdbcWalletRepository.insert(new Wallet(UUID.randomUUID(), insertCustomer.getCustomerId()));

        //when
        jdbcWalletRepository.deleteVoucherByVoucherId(insertCustomer.getCustomerId(), voucherId);

        //then
    }

    @Test
    @DisplayName("해당 voucher를 가진 customer 조회")
    void findCustomerByVoucherId() {
        //given
        //Customer customer2 = jdbcCustomerRepository.insert(new Customer(UUID.randomUUID(), "tester02", false));

        jdbcWalletRepository.insert(new Wallet(UUID.randomUUID(), insertCustomer.getCustomerId()));
        //jdbcWalletRepository.insert(new Wallet(UUID.randomUUID(), customer2.getCustomerId()));

        //when
        Customer findCustomers = jdbcWalletRepository.findCustomerByVoucherId(insertVoucher.getVoucherId()).get();

        //then
        //assertThat(findCustomers.size(), is(2));
    }
}