package com.example.voucherproject.common.config;

import com.example.voucherproject.common.io.console.Input;
import com.example.voucherproject.common.io.console.Output;
import com.example.voucherproject.common.io.file.MyReader;
import com.example.voucherproject.common.io.file.MyWriter;
import com.example.voucherproject.user.repository.UserFileRepository;
import com.example.voucherproject.user.repository.UserRepository;
import com.example.voucherproject.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfig {

    @Bean
    public UserService userService(Input input, Output output, UserRepository userRepository){
        return new UserService(input, output, userRepository);
    }
    @Bean
    public UserRepository userRepository(MyReader reader, MyWriter writer){
        return new UserFileRepository(reader, writer);
    }

}
