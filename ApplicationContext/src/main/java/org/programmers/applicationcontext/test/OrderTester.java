package org.programmers.applicationcontext.test;
import org.programmers.applicationcontext.config.AppConfiguration;
import org.programmers.applicationcontext.order.OrderItem;
import org.programmers.applicationcontext.order.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var customerId = UUID.randomUUID();

        var orderService = applicationContext.getBean(OrderService.class);

        // 100원짜리 제품을 1개 수량
        var order = orderService.createOrder(customerId, List.of(new OrderItem(UUID.randomUUID(), 100L, 1)));

        Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("total amount is not 100L", order.totalAmount()));

    }
}
