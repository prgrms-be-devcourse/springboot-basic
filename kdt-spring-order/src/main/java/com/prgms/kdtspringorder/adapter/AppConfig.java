package com.prgms.kdtspringorder.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prgms.kdtspringorder.application.OrderService;
import com.prgms.kdtspringorder.application.VoucherService;
import com.prgms.kdtspringorder.domain.model.order.Order;
import com.prgms.kdtspringorder.domain.model.order.OrderRepository;
import com.prgms.kdtspringorder.domain.model.voucher.Voucher;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherRepository;

@Configuration
public class AppConfig {
    @Bean
    public OrderService orderService() {
        return new OrderService(orderRepository(), voucherService());
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void save(Order order) {
                System.out.println("save order");
            }
        };
    }

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
        };
    }
}
