package com.example.demo.common;

import com.example.demo.order.domain.Order;
import com.example.demo.order.domain.repository.OrderRepository;
import com.example.demo.order.application.OrderService;
import com.example.demo.common.io.impl.ConsoleInput;
import com.example.demo.common.io.impl.ConsoleOutput;
import com.example.demo.common.io.Input;
import com.example.demo.common.io.Output;
import com.example.demo.voucher.application.VoucherCommand;
import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.domain.repository.VoucherRepository;
import com.example.demo.voucher.application.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherCommand create() {
        return VoucherService::createVoucher;
    }

    @Bean
    public VoucherCommand list() {
        return VoucherService::listVouchers;
    }

    @Bean
    public List<Voucher> vouchers() {
        return new ArrayList<Voucher>();
    }

    @Bean
    public VoucherRepository voucherRepository(List<Voucher> vouchers) {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }

            @Override
            public List<Voucher> findAll() {
                return new ArrayList<>(vouchers);
            }

            @Override
            public void insert(Voucher voucher) {
                vouchers.add(voucher);
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
    public VoucherService voucherService(VoucherRepository voucherRepository, Input consoleInput, Output consoleOutput) {
        return new VoucherService(voucherRepository, consoleInput, consoleOutput);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }

    @Bean
    public Input consoleInput() {
        return new ConsoleInput();
    }

    @Bean
    public Output consoleOutput() {
        return new ConsoleOutput();
    }

}
