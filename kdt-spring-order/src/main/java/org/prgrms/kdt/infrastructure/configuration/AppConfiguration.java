package org.prgrms.kdt.infrastructure.configuration;

import org.prgrms.kdt.domain.order.domain.Order;
import org.prgrms.kdt.domain.order.repository.OrderRepository;
import org.prgrms.kdt.domain.order.service.OrderService;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.prgrms.kdt.infrastructure.voucher.VoucherMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* 레포지토리 생성에 대한 책임을 가지고 있음.
* 각 서비스들이 어떤 레포지토리를 사용하는지 관계에 대한 책임도 가지고 있음.
* */
@Configuration
public class AppConfiguration {
    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherMemoryRepository();
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
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
