package com.prgrms.w3springboot;

import com.prgrms.w3springboot.io.CommandLine;
import com.prgrms.w3springboot.io.Console;
import com.prgrms.w3springboot.order.repository.MemoryOrderRepository;
import com.prgrms.w3springboot.order.repository.OrderRepository;
import com.prgrms.w3springboot.order.service.OrderService;
import com.prgrms.w3springboot.voucher.repository.MemoryVoucherRepository;
import com.prgrms.w3springboot.voucher.repository.VoucherRepository;
import com.prgrms.w3springboot.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }

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

    @Bean
    public Console console() {
        return new Console();
    }

    @Bean
    public CommandLine commandLine(Console console, VoucherService voucherService) {
        return new CommandLine(console, voucherService);
    }

}
