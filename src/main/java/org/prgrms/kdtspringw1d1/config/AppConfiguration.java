package org.prgrms.kdtspringw1d1.config;

import org.prgrms.kdtspringw1d1.order.Order;
import org.prgrms.kdtspringw1d1.order.OrderRepository;
import org.prgrms.kdtspringw1d1.order.OrderService;
import org.prgrms.kdtspringw1d1.voucher.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.Optional;
import java.util.UUID;

@Configuration
@ComponentScan(basePackageClasses = {Order.class, Voucher.class})
public class AppConfiguration {

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void save(Order order) {
            }

            @Override
            public void insert(Order order) {

            }
        };
    }

}