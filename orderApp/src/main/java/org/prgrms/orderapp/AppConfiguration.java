package org.prgrms.orderapp;

import org.prgrms.orderapp.repository.InMemoryRepository;
import org.prgrms.orderapp.repository.OrderRepository;
import org.prgrms.orderapp.repository.VoucherRepository;
import org.prgrms.orderapp.service.OrderService;
import org.prgrms.orderapp.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new InMemoryRepository();
    }

    @Bean
    public OrderRepository orderRepository() {
        return order -> {};
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }
}
