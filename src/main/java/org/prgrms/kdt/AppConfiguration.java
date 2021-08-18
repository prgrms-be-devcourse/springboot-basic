package org.prgrms.kdt;

import org.prgrms.kdt.engine.order.Order;
import org.prgrms.kdt.engine.order.OrderRepository;
import org.prgrms.kdt.engine.order.OrderService;
import org.prgrms.kdt.engine.voucher.Voucher;
import org.prgrms.kdt.engine.voucher.VoucherRepository;
import org.prgrms.kdt.engine.voucher.VoucherService;
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
            private List<Voucher> voucherData = new ArrayList<>();

            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }

            @Override
            public void insertVoucher(Voucher voucher) {
                voucherData.add(voucher);
            }
        };
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {}
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
