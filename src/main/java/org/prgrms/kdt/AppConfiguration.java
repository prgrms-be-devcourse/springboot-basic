package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderRepository;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

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
