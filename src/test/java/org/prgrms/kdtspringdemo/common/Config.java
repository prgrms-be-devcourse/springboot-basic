package org.prgrms.kdtspringdemo.common;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdtspringdemo.console.VoucherOperator;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.voucher.repository.JdbcVoucherRepository;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.wallet.WalletService;
import org.prgrms.kdtspringdemo.wallet.repository.JdbcWalletRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {
        "org.prgrms.kdtspringdemo",
        "org.prgrms.kdtspringdemo.console",
})
public class Config {
    @Bean
    public DataSource dataSource() {
//          docker에 올린 DB로 시작하기(mysql v8.0.11)
        var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/customer_mgmt")
                .username("root")
                .password("root1234!")
                .type(HikariDataSource.class)
                .build();
        return dataSource;
    }

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcVoucherRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public VoucherService voucherService(JdbcVoucherRepository jdbcVoucherRepository) {
        return new VoucherService(jdbcVoucherRepository);
    }

    @Bean
    public JdbcWalletRepository jdbcWalletRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcWalletRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public WalletService walletService(JdbcWalletRepository jdbcWalletRepository) {
        return new WalletService(jdbcWalletRepository);
    }

    @Bean
    public VoucherOperator voucherOperator(VoucherService voucherService, WalletService walletService, CustomerService customerService) {
        return new VoucherOperator(voucherService, walletService, customerService);
    }
}
