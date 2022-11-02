package com.programmers.commandlind;

import com.programmers.commandlind.config.AppConfiguration;
import com.programmers.commandlind.entity.OrderItem;
import com.programmers.commandlind.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;
/**
*
*   OrderTest의 설명을 여기에 작성한다.
*
*   @author JangJuYeong
*   @version 1.0.0
*   작성일 2022/11/02
*
**/
public class OrderTest {
    public static void main(String[] args) {
        final var annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var customerId = UUID.randomUUID();

        final var orderService = annotationConfigApplicationContext.getBean(OrderService.class);

        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }});

        Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));
    }
}
