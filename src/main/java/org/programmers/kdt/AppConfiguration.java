package org.programmers.kdt;

import org.programmers.kdt.order.Order;
import org.programmers.kdt.order.OrderRepository;
import org.programmers.kdt.order.OrderSerivce;
import org.programmers.kdt.voucher.MemoryVoucherRepository;
import org.programmers.kdt.voucher.VoucherRepository;
import org.programmers.kdt.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public OrderSerivce orderSerivce(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderSerivce(voucherService, orderRepository);
    }
}
