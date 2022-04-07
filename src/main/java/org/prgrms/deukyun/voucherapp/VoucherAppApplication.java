package org.prgrms.deukyun.voucherapp;

import org.prgrms.deukyun.voucherapp.config.AppConfig;
import org.prgrms.deukyun.voucherapp.voucher.FixedAmountVoucher;
import org.prgrms.deukyun.voucherapp.order.Order;
import org.prgrms.deukyun.voucherapp.order.OrderItem;
import org.prgrms.deukyun.voucherapp.voucher.Voucher;
import org.prgrms.deukyun.voucherapp.voucher.VoucherRepository;
import org.prgrms.deukyun.voucherapp.order.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
@SpringBootApplication
public class VoucherAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(VoucherAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        Environment environment = ac.getEnvironment();
        String version = environment.getProperty("kdt.version");
        List supportVendors = environment.getProperty("kdt.support-vendors", List.class);
        Integer minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
        System.out.println(MessageFormat.format("version -> {0}", version));
        System.out.println(MessageFormat.format("supportVendors -> {0}", supportVendors));
        System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", minimumOrderAmount));

        UUID customerId = UUID.randomUUID();

        VoucherRepository voucherRepository = ac.getBean(VoucherRepository.class);
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        OrderService orderService = ac.getBean(OrderService.class);

        List<OrderItem> orderItems = Arrays.asList(new OrderItem(UUID.randomUUID(), 100L, 1));
        Order order = orderService.createOrder(customerId, orderItems, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90, MessageFormat.format("totalAmount {0}", order.totalAmount()));
    }
}
