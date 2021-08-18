package org.prgrms.kdt.config;

import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderRepository;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 7:51 오후
 */
@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public void save(Voucher voucher) {
                /**
                 * 메소드가 추가되어 오버라이드
                 * 미션코드에 영향을 주지 않는다.
                 */
            }

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