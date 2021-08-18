package org.prgrms.kdtbespring;

import org.prgrms.kdtbespring.config.AppConfiguration;
import org.prgrms.kdtbespring.entity.Order;
import org.prgrms.kdtbespring.service.OrderService;
import org.prgrms.kdtbespring.vo.OrderItem;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        // IoC 컨테이너에 AppConfiguration을 등록
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        UUID customerId = UUID.randomUUID();

        // IoC 컨테이너 안에 있는 Bean 중 OrderService 객체를 받아온다.
        OrderService orderService = annotationConfigApplicationContext.getBean(OrderService.class);

        ArrayList<OrderItem> orderItems = new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};

        Order order = orderService.createOrder(customerId, orderItems);

        Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("totalAmount {0} is not 100L", order.totalAmount()));
    }
}
