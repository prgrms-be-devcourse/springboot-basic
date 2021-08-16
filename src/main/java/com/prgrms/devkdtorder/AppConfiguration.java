package com.prgrms.devkdtorder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {


    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }

    @Bean
    public VoucherRepository voucherRepository() {
        return new MemVoucherRepository();
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

}
