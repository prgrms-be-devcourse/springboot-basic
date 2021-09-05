package org.prgrms.kdt.config;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdt.repository.customer.CustomerRepository;
import org.prgrms.kdt.repository.customer.JdbcCustomerRepository;
import org.prgrms.kdt.repository.voucher.JdbcVoucherRepository;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.prgrms.kdt.repository.wallet.JdbcWalletRepository;
import org.prgrms.kdt.repository.wallet.WalletRepository;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.WalletService;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public
class TestConfig {
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/order_mgmt_test")
                .username("root")
                .password("admin")
                .type(HikariDataSource.class)
                .build();
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public VoucherRepository voucherRepository() {
        return new JdbcVoucherRepository(jdbcTemplate());
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new JdbcCustomerRepository(jdbcTemplate());
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService(customerRepository());
    }

    @Bean
    public WalletRepository walletRepository() {
        return new JdbcWalletRepository(jdbcTemplate(), customerRepository(), voucherRepository());
    }

    @Bean
    public WalletService walletService() {
        return new WalletService(walletRepository(), customerService(), voucherService());
    }
}
