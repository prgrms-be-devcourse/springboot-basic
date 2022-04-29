package com.example.voucherproject.common.config;

import com.example.voucherproject.user.service.UserService;
import com.example.voucherproject.voucher.service.VoucherService;
import com.example.voucherproject.wallet.repository.WalletJdbcRepository;
import com.example.voucherproject.wallet.repository.WalletRepository;
import com.example.voucherproject.wallet.service.WalletService;
import com.example.voucherproject.wallet.service.WalletWebService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class WalletServiceConfig {

    @Bean
    public WalletRepository walletRepository(JdbcTemplate jdbcTemplate){
        return new WalletJdbcRepository(jdbcTemplate);
    }
    @Bean
    public WalletService walletService(WalletRepository walletRepository, UserService userService, VoucherService voucherService){
        return new WalletWebService(walletRepository, userService, voucherService);
    }
}
