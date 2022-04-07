package org.prgrms.deukyun.voucherapp.config;

import org.prgrms.deukyun.voucherapp.entity.Order;
import org.prgrms.deukyun.voucherapp.entity.Voucher;
import org.prgrms.deukyun.voucherapp.repository.OrderRepository;
import org.prgrms.deukyun.voucherapp.repository.VoucherRepository;
import org.prgrms.deukyun.voucherapp.service.OrderService;
import org.prgrms.deukyun.voucherapp.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfig {

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
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
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }
}
