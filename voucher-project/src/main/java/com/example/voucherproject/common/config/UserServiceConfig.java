package com.example.voucherproject.common.config;

import com.example.voucherproject.user.repository.UserJdbcRepository;
import com.example.voucherproject.user.repository.UserRepository;
import com.example.voucherproject.user.service.UserWebService;
import com.example.voucherproject.wallet.repository.WalletRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class UserServiceConfig {

    @Bean
    public UserRepository userRepository(JdbcTemplate jdbcTemplate){
        return new UserJdbcRepository(jdbcTemplate);
    }

    @Bean
    public UserWebService UserService(UserRepository userRepository, WalletRepository walletRepository){
        return new UserWebService(userRepository, walletRepository);
    }

}
