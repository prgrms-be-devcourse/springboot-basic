package org.prgrms.kdtspringw1d1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(UUID.randomUUID(), new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100, 1));
        }}, UUID.randomUUID());
        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
    }
}
