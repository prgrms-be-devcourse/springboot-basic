package org.prgrms.kdtspringdemo.wallet.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.repository.JdbcCustomerRepository;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
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

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void insert() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "tester01", true);
        jdbcCustomerRepository.insert(customer);
        List<UUID> vouchers = new ArrayList<>();
        vouchers.add(UUID.randomUUID());
        Wallet wallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), vouchers);

        //when
        Wallet insertWallet = jdbcWalletRepository.insert(wallet);

        //then
        assertThat(wallet.getWalletId(), is(insertWallet.getWalletId()));
    }

    @Test
    void findAllVoucherByCustomerId() {
    }

    @Test
    void deleteVoucherByVoucherId() {
    }

    @Test
    void findCustomerByVoucherId() {
    }
}