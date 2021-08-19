package org.prgrms.orderapp;

import org.prgrms.orderapp.config.AppConfiguration;
import org.prgrms.orderapp.model.OrderItem;
import org.prgrms.orderapp.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var customerId = UUID.randomUUID();
        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(customerId, new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, UUID.randomUUID());
        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
    }
}
