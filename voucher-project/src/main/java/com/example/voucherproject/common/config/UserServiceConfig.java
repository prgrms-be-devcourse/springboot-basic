package com.example.voucherproject.common.config;

import com.example.voucherproject.common.file.MyReader;
import com.example.voucherproject.common.file.MyWriter;
import com.example.voucherproject.common.io.Input;
import com.example.voucherproject.common.io.Output;
import com.example.voucherproject.user.domain.UserFactory;
import com.example.voucherproject.user.repository.UserFileRepository;
import com.example.voucherproject.user.repository.UserRepository;
import com.example.voucherproject.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfig {

    @Bean
    public UserService userService(Input input, Output output, UserFactory userFactory, UserRepository userRepository){
        return new UserService(input, output, userFactory, userRepository);
    }
    @Bean
    public UserRepository userRepository(MyReader reader, MyWriter writer){
        return new UserFileRepository(reader, writer);
    }

    @Bean
    public UserFactory userFactory(){
        return new UserFactory();
    }
}
