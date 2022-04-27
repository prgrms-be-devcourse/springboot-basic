package com.example.voucherproject.common.config;

import com.example.voucherproject.voucher.repository.VoucherJdbcRepository;
import com.example.voucherproject.voucher.repository.VoucherRepository;
import com.example.voucherproject.voucher.service.VoucherService;
import com.example.voucherproject.voucher.service.VoucherWebService;
import com.example.voucherproject.wallet.repository.WalletRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VoucherServiceConfig {

    @Bean VoucherRepository voucherRepository(JdbcTemplate jdbcTemplate){
        return new VoucherJdbcRepository(jdbcTemplate);
    }
    @Bean
    VoucherService voucherService(VoucherRepository voucherRepository, WalletRepository walletRepository){
        return new VoucherWebService(voucherRepository, walletRepository);
    }

}
