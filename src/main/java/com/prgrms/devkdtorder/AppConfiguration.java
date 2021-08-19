package com.prgrms.devkdtorder;

import com.prgrms.devkdtorder.cla.CommandLineApplication;
import com.prgrms.devkdtorder.cla.Input;
import com.prgrms.devkdtorder.cla.TextIOConsole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
@ComponentScan
public class AppConfiguration {


    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }


}
