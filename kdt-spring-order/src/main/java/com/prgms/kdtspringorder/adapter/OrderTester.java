package com.prgms.kdtspringorder.adapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import com.prgms.kdtspringorder.application.OrderService;
import com.prgms.kdtspringorder.config.AppConfig;
import com.prgms.kdtspringorder.domain.model.order.OrderItem;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        var customerId = UUID.randomUUID();
        var orderService = applicationContext.getBean(OrderService.class);
        var orderItems = new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};
        var order = orderService.createOrder(customerId, orderItems); // without voucher
        Assert.isTrue(order.calculateTotalAmount() == 100L,
            MessageFormat.format("totalAmount {0} is not 100L", order.calculateTotalAmount()));

        /*var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        var percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, percentDiscountVoucher);

        Assert.isTrue(order.calculateTotalAmount() == 90L,
            MessageFormat.format("totalAmount {0} is not 90L", order.calculateTotalAmount()));*/
    }
}
