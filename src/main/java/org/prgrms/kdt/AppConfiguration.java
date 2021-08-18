package org.prgrms.kdt;

import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.Order;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.repository.OrderRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                return vouchers.stream().findAny().filter(v -> v.getVoucherId() == voucherId);
            }

            @Override
            public void create(int type) {
                if (type==1) {
                    vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), 1));
                } else if(type==2) {
                    vouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), 1));
                }
            }

            @Override
            public List<Voucher> list() {
                return vouchers;
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
