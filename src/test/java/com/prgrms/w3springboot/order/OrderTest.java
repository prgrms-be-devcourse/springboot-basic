package com.prgrms.w3springboot.order;

import com.prgrms.w3springboot.configuration.AppConfig;
import com.prgrms.w3springboot.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

class OrderTest {

    @DisplayName("의존성 주입으로 주문 생성 테스트")
    @Test
    void testCreateOrderByDI() {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(UUID.randomUUID(), List.of(
                new OrderItem(UUID.randomUUID(), 100L, 1))
        );

        Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("totalAmount is not {0}", order.totalAmount()));
    }

}