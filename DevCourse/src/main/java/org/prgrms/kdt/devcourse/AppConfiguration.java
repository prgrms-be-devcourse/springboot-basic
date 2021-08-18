package org.prgrms.kdt.devcourse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }

    @Bean
    public OrderRepository orderRepository(){
        return new OrderRepository() {
            @Override
            public void save(Order order) {

            }
        };
    }

    @Bean
    public VoucherService voucherService(){
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderService(){
        return new OrderService(voucherService(),orderRepository());
    }
}
