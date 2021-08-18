package org.prgrms.kdt;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        // Java 기반의 설정(AppConfiguration class)을 이용할 경우 ApplicationContext의
        // AnnotationConfigApplicationContext 구현체를 사용한다.
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var orderService = applicationContext.getBean(OrderService.class); // bean의 이름이나 클래스의 타입을 통해 빈객체를 갖고올 수 있다.

        var customerId = UUID.randomUUID();
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }});

        Assert.isTrue(order.totalAmount() == 100L,
                MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
    }
}
