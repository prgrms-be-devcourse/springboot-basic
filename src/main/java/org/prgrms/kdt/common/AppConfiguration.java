package org.prgrms.kdt.common;

import org.prgrms.kdt.domain.*;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.strategy.Voucher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }

            @Override
            public List<Voucher> findAll() {
                return new ArrayList<>();
            }
        };
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
