package com.prgrm.kdt;

import com.prgrm.kdt.order.application.OrderService;
import com.prgrm.kdt.order.repository.MemoryOrderRepository;
import com.prgrm.kdt.order.repository.OrderRepository;
import com.prgrm.kdt.voucher.application.VoucherService;
import com.prgrm.kdt.voucher.repository.MemoryVoucherRepository;
import com.prgrm.kdt.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    };

    @Bean
    public OrderRepository orderRepository() {
        return new MemoryOrderRepository();
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
