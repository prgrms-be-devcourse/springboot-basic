package com.programmers.commandlind.config;

import com.programmers.commandlind.entity.Order;
import com.programmers.commandlind.entity.Voucher;
import com.programmers.commandlind.repository.OrderRepository;
import com.programmers.commandlind.repository.VoucherRespository;
import com.programmers.commandlind.service.OrderService;
import com.programmers.commandlind.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRespository voucherRespository() {
        return new VoucherRespository() {
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
        return new VoucherService(voucherRespository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }
}

