package org.prgrms.kdtbespring.config;

import org.prgrms.kdtbespring.entity.Order;
import org.prgrms.kdtbespring.entity.Voucher;
import org.prgrms.kdtbespring.repository.OrderRepository;
import org.prgrms.kdtbespring.repository.VoucherRepository;
import org.prgrms.kdtbespring.service.OrderService;
import org.prgrms.kdtbespring.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

/**
 * VoucherService, VoucherRepository
 * OrserService, OrderRepository
 * 에 대한 생성에 대한 책임을 갖게 됨.
 * 그리고 각각의 의존 관계를 맺는것을 담당하게 됨.
 */
@Configuration
public class AppConfiguration {

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
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }
}
