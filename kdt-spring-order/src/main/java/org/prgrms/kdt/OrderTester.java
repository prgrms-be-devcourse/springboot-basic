package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ArrayList<OrderItem> orderItems = new ArrayList<>(Arrays.asList(new OrderItem(UUID.randomUUID(), 100, 1)));
        UUID customerId = UUID.randomUUID();
        VoucherRepository voucherRepository = applicationContext.getBean(VoucherRepository.class);
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        OrderService orderService = applicationContext.getBean(OrderService.class);
        Order order = orderService.createOrder(customerId, orderItems, voucher.getVoucherId());
        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));

    }
}
