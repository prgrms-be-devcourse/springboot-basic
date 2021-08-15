package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.order.OrderItem;
import com.programmers.kdtspringorder.order.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        OrderService orderService = applicationContext.getBean(OrderService.class);
        var customerId = UUID.randomUUID();
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }});


        Assert.isTrue(order.totalAmount() == 100L, "totalAmount {0} is not 10L" + order.totalAmount());

    }
}
