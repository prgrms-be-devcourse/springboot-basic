package org.prgrms.kdt.config;

import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.order.application.OrderService;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return voucherId -> Optional.empty();
    }

    @Bean
    public OrderRepository orderRepository() {
        return order -> {

        };
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository, OrderRepository orderRepository) {
        return new VoucherService(voucherRepository, orderRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }
}
