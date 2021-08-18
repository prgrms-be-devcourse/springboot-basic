package org.prgrms.kdt;

import org.prgrms.kdt.domain.OrderItem;
import org.prgrms.kdt.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(UUID.randomUUID(), new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100, 1));
        }});
        Assert.isTrue(order.totalAmount() == 100L,
                MessageFormat.format("totalAmount({0}) is not 100L", order.totalAmount()));
    }

}
