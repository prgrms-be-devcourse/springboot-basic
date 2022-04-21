package com.example.voucherproject.common.config;

import com.example.voucherproject.common.console.Input;
import com.example.voucherproject.common.console.Output;
import com.example.voucherproject.user.repository.UserRepository;
import com.example.voucherproject.voucher.repository.VoucherRepository;
import com.example.voucherproject.wallet.repository.WalletJdbcRepository;
import com.example.voucherproject.wallet.repository.WalletRepository;
import com.example.voucherproject.wallet.WalletService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class WalletServiceConfig {

    @Bean
    public WalletService walletService(Input input, Output output,
                                       UserRepository userRepository,
                                       VoucherRepository voucherRepository,
                                       WalletRepository walletRepository){

        return new WalletService(input, output, userRepository, voucherRepository, walletRepository);
    }

    @Bean
    public WalletRepository walletRepository(JdbcTemplate jdbcTemplate){
        return new WalletJdbcRepository(jdbcTemplate);
    }
}
