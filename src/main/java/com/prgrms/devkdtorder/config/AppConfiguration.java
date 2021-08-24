package com.prgrms.devkdtorder.config;

import com.prgrms.devkdtorder.cla.CommandLineApplication;
import com.prgrms.devkdtorder.order.domain.Order;
import com.prgrms.devkdtorder.order.repository.OrderRepository;
import com.prgrms.devkdtorder.voucher.domain.Voucher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.prgrms.devkdtorder"})
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
