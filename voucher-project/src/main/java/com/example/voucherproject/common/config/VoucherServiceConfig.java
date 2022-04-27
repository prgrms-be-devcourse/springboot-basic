package com.example.voucherproject.common.config;

import com.example.voucherproject.common.console.Input;
import com.example.voucherproject.common.console.Output;
import com.example.voucherproject.voucher.repository.VoucherJdbcRepository;
import com.example.voucherproject.voucher.repository.VoucherRepository;
import com.example.voucherproject.voucher.service.VoucherService;
import com.example.voucherproject.wallet.repository.WalletRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VoucherServiceConfig {

    @Bean
    VoucherService voucherService(Input input, Output output, VoucherRepository voucherRepository, WalletRepository walletRepository){
        return new VoucherService(input, output, voucherRepository, walletRepository);
    }

//    @Bean public VoucherRepository voucherRepository(MyReader reader, MyWriter writer){
//        return new VoucherFileRepository(reader, writer);
//    }

    @Bean VoucherRepository voucherRepository(JdbcTemplate jdbcTemplate){
        return new VoucherJdbcRepository(jdbcTemplate);
    }
}
