package org.prgrms.orderApp.test;

import org.prgrms.orderApp.AppConfiguration;
import org.prgrms.orderApp.model.order.OrderItem;
import org.prgrms.orderApp.model.voucher.FixedAmountVoucher;
import org.prgrms.orderApp.service.OrderService;
import org.prgrms.orderApp.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var customerId = UUID.randomUUID();
        var voucherId = UUID.randomUUID();

        var VourcherService = applicationContext.getBean(VoucherService.class);
        VourcherService.saveVoucher(new FixedAmountVoucher(voucherId,10L));

        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(customerId, new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucherId);
        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
    }
}
