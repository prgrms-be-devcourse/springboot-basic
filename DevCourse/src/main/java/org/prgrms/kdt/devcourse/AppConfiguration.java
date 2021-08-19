package org.prgrms.kdt.devcourse;

import org.prgrms.kdt.devcourse.order.Order;
import org.prgrms.kdt.devcourse.order.OrderRepository;
import org.prgrms.kdt.devcourse.order.OrderService;
import org.prgrms.kdt.devcourse.voucher.MemoryVoucherRepository;
import org.prgrms.kdt.devcourse.voucher.VoucherRepository;
import org.prgrms.kdt.devcourse.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }

    @Bean
    public OrderRepository orderRepository(){
        return order -> {

        };
    }

    @Bean
    public VoucherService voucherService(){
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderService(){
        return new OrderService(voucherService(),orderRepository());
    }
}
