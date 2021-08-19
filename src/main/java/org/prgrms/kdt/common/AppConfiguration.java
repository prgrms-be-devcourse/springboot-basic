package org.prgrms.kdt.common;

import org.prgrms.kdt.domain.*;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new Vouchers();
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
    public VoucherMachine voucherMachine() {
        return new VoucherMachine();
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository, VoucherMachine voucherMachine) {
        return new VoucherService(voucherRepository, voucherMachine);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }
}
