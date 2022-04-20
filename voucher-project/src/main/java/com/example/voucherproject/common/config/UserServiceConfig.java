package com.example.voucherproject.common.config;

import com.example.voucherproject.common.console.Input;
import com.example.voucherproject.common.console.Output;
import com.example.voucherproject.user.repository.UserJdbcRepository;
import com.example.voucherproject.user.repository.UserRepository;
import com.example.voucherproject.user.service.UserService;
import com.example.voucherproject.wallet.repository.WalletRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class UserServiceConfig {

    @Bean
    UserService userService(Input input, Output output, UserRepository userRepository, WalletRepository walletRepository){
        return new UserService(input, output, userRepository, walletRepository);
    }

//    @Bean UserRepository userRepository(MyReader reader, MyWriter writer){
//        return new UserFileRepository(reader, writer);
//    }

    @Bean UserRepository userRepository(JdbcTemplate jdbcTemplate){
        return new UserJdbcRepository(jdbcTemplate);
    }

}
