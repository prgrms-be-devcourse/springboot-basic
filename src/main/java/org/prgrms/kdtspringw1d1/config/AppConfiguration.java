package org.prgrms.kdtspringw1d1.config;

import org.prgrms.kdtspringw1d1.order.Order;
import org.prgrms.kdtspringw1d1.order.OrderRepository;
import org.prgrms.kdtspringw1d1.order.OrderService;
import org.prgrms.kdtspringw1d1.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringw1d1.voucher.Voucher;
import org.prgrms.kdtspringw1d1.voucher.VoucherRepository;
import org.prgrms.kdtspringw1d1.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.of(new FixedAmountVoucher(voucherId, 10));
            }
        };
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void save(Order order) {
            }

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