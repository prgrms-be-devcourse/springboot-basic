package org.prgms;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfiguration {
    @Bean
    public Io io(){
        return new Io();
    }

    @Bean
    public VoucherRepository voucherRepository(){
        return new MemoryVoucherRepository();
    }

    @Bean
    public OrderRepository orderRepository(){
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    @Bean
    public VoucherService voucherService(){
        return new VoucherService(voucherRepository());
    };

    @Bean
    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }

}
