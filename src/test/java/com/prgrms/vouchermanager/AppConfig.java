package com.prgrms.vouchermanager;

import com.prgrms.vouchermanager.repository.customer.BlacklistFileRepository;
import com.prgrms.vouchermanager.repository.customer.CustomerRepository;
import com.prgrms.vouchermanager.repository.voucher.VoucherFileRepository;
import com.prgrms.vouchermanager.repository.voucher.VoucherJdbcRepository;
import com.prgrms.vouchermanager.repository.voucher.VoucherMemoryRepository;
import com.prgrms.vouchermanager.repository.wallet.WalletRepository;
import com.prgrms.vouchermanager.service.CustomerService;
import com.prgrms.vouchermanager.service.VoucherService;
import com.prgrms.vouchermanager.service.WalletService;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/voucher_manager?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
                .username("root")
                .password("suzzingV1999@")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public WalletRepository walletRepository() {
        return new WalletRepository(jdbcTemplate());
    }

    @Bean
    public VoucherJdbcRepository voucherJdbcRepository() {
        return new VoucherJdbcRepository(jdbcTemplate().getDataSource());
    }

    @Bean
    public VoucherMemoryRepository voucherMemoryRepository() {
        return new VoucherMemoryRepository();
    }

    @Bean
    public VoucherFileRepository voucherFileRepository() {
        return new VoucherFileRepository("src/main/resources/voucher_list.csv");
    }

    @Bean
    public BlacklistFileRepository blacklistFileRepository() {
        return new BlacklistFileRepository("src/main/resources/customer_blacklist.csv");
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepository(jdbcTemplate().getDataSource(),
                blacklistFileRepository());
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService(blacklistFileRepository(), customerRepository());
    }

    @Bean
    public WalletService walletService() {
        return new WalletService(walletRepository(), customerRepository(), voucherJdbcRepository());
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherJdbcRepository());
    }
}
