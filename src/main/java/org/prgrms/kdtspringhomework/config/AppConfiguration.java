package org.prgrms.kdtspringhomework.config;

import org.prgrms.kdtspringhomework.order.domain.Order;
import org.prgrms.kdtspringhomework.order.repository.OrderRepository;
import org.prgrms.kdtspringhomework.order.service.OrderService;
import org.prgrms.kdtspringhomework.voucher.domain.Voucher;
import org.prgrms.kdtspringhomework.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdtspringhomework.voucher.repository.VoucherRepository;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
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

    //어떤 Service와 Repository를 쓰는지 관계 책임
    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }
}
