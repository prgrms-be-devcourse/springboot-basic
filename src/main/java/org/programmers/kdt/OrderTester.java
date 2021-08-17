package org.programmers.kdt;

import org.programmers.kdt.order.OrderItem;
import org.programmers.kdt.order.OrderSerivce;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        UUID customerId = UUID.randomUUID();
        OrderSerivce orderService = applicationContext.getBean(OrderSerivce.class);

        List<OrderItem> orderItems = new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};

        var order = orderService.createOrder(customerId, orderItems);

        Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("totalAmount {0} is not 100L", order.totalAmount()));

    }
}
