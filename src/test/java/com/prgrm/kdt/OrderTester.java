package com.prgrm.kdt;

import com.prgrm.kdt.order.application.OrderService;
import com.prgrm.kdt.order.domain.Order;
import com.prgrm.kdt.order.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTester {

    @Test
    public void testDefaultOrder() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        UUID customerId = UUID.randomUUID();
        OrderService orderService = applicationContext.getBean(OrderService.class);
        Order order = orderService.createOrder(customerId, new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }});
        assertThat(order.totalAmount()).isEqualTo(100L);
    }
}