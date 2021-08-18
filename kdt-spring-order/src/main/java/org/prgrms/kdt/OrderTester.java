package org.prgrms.kdt;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class); // ApplicationContext

        var customerId = UUID.randomUUID();
        var orderService= applicationContext.getBean(OrderService.class); // Bean
        var order = orderService.createOder(customerId, new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }});

        Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("total amount {0} is not 100L", order.totalAmount()));
    }
}
