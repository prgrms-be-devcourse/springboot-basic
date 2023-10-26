package org.prgrms.kdtspringdemo.wallet.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.repository.JdbcCustomerRepository;
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
@ActiveProfiles({"DB"})
class JdbcWalletRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo.wallet","org.prgrms.kdtspringdemo.customer"})
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
    JdbcCustomerRepository jdbcCustomerRepository;
    private final Logger logger = LoggerFactory.getLogger(JdbcWalletRepositoryTest.class);
    Customer insertCustomer;

    @BeforeEach
    void init() {
        Customer customer = new Customer(UUID.randomUUID(), "tester01", true);
        jdbcCustomerRepository.insert(customer);
        insertCustomer = customer;
    }

    @Test
    @DisplayName("지갑을 추가합니다.")
    void insert() {
        //given
        List<UUID> vouchers = new ArrayList<>();
        vouchers.add(UUID.randomUUID());
        Wallet wallet = new Wallet(UUID.randomUUID(), insertCustomer.getCustomerId(), vouchers);

        //when
        Wallet insertWallet = jdbcWalletRepository.insert(wallet);

        //then
        assertThat(wallet.getWalletId(), is(insertWallet.getWalletId()));
    }

    @Test
    @DisplayName("walletId로 지갑을 검색합니다.")
    void findWalletByWalletId() {
        //given
        List<UUID> vouchers = new ArrayList<>();
        vouchers.add(UUID.randomUUID());
        Wallet wallet = jdbcWalletRepository.insert(new Wallet(UUID.randomUUID(), insertCustomer.getCustomerId(), vouchers));

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
        jdbcWalletRepository.insert(new Wallet(UUID.randomUUID(), insertCustomer.getCustomerId(), vouchers));

        //when
        List<UUID> findVouchers = jdbcWalletRepository.findVouchersByCustomerId(insertCustomer.getCustomerId()).get();

        //then
        assertThat(findVouchers, samePropertyValuesAs(vouchers));
    }

    @Test
    @DisplayName("wallet안의 vouchers에서 데이터 삭제")
    void deleteVoucherByVoucherId() {
        //given
        List<UUID> vouchers = new ArrayList<>();
        UUID voucherId = UUID.randomUUID();
        vouchers.add(voucherId);
        Wallet insertWallet = jdbcWalletRepository.insert(new Wallet(UUID.randomUUID(), insertCustomer.getCustomerId(), vouchers));

        //when
        List<UUID> updatedVouchers = jdbcWalletRepository.deleteVoucherByVoucherId(insertCustomer.getCustomerId(), voucherId);

        //then
        assertThat(updatedVouchers.indexOf(voucherId), is(-1));
    }

    @Test
    void findCustomerByVoucherId() {
    }
}