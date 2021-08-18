package org.prgrms.kdt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {
    // 의존 관계 형성

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
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }
}
