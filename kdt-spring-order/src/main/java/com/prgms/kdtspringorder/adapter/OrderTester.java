package com.prgms.kdtspringorder.adapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import com.prgms.kdtspringorder.application.OrderService;
import com.prgms.kdtspringorder.config.AppConfig;
import com.prgms.kdtspringorder.domain.model.order.Order;
import com.prgms.kdtspringorder.domain.model.order.OrderItem;
import com.prgms.kdtspringorder.domain.model.voucher.FixedAmountVoucher;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        var customerId = UUID.randomUUID();
        var orderService = applicationContext.getBean(OrderService.class);
        var orderItems = new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};

        // without voucher
        /*var order = orderService.createOrder(customerId, orderItems);
        Assert.isTrue(order.calculateTotalAmount() == 100L,
            MessageFormat.format("totalAmount {0} is not 100L", order.calculateTotalAmount()));*/

        // with voucher
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 200L);
        // var percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, fixedAmountVoucher);

        long totalAmount = order.calculateTotalAmount();
        Assert.isTrue(totalAmount == 0L,
            MessageFormat.format("totalAmount {0} is not 90L", totalAmount));
    }
}
