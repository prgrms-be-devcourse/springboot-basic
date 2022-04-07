package org.prgrms.deukyun.voucherapp;

import org.prgrms.deukyun.voucherapp.order.entity.Order;
import org.prgrms.deukyun.voucherapp.order.entity.OrderItem;
import org.prgrms.deukyun.voucherapp.order.service.OrderService;
import org.prgrms.deukyun.voucherapp.voucher.entity.FixedAmountVoucher;
import org.prgrms.deukyun.voucherapp.voucher.entity.Voucher;
import org.prgrms.deukyun.voucherapp.voucher.repository.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.deukyun.voucherapp.order", "org.prgrms.deukyun.voucherapp.voucher"})
@SpringBootApplication
public class VoucherAppApplication {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(VoucherAppApplication.class, args);
        UUID customerId = UUID.randomUUID();

        VoucherRepository voucherRepository = ac.getBean(VoucherRepository.class);
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        OrderService orderService = ac.getBean(OrderService.class);

        List<OrderItem> orderItems = Arrays.asList(new OrderItem(UUID.randomUUID(), 100L, 1));
        Order order = orderService.createOrder(customerId, orderItems, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90, MessageFormat.format("totalAmount {0}", order.totalAmount()));
    }
}
