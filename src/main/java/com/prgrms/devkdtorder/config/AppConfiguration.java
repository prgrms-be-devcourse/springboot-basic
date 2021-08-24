package com.prgrms.devkdtorder.config;

import com.prgrms.devkdtorder.cla.CommandLineApplication;
import com.prgrms.devkdtorder.order.domain.Order;
import com.prgrms.devkdtorder.order.repository.OrderRepository;
import com.prgrms.devkdtorder.voucher.domain.Voucher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.prgrms.devkdtorder"})
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
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
