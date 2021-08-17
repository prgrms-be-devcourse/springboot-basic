package org.prgrms.kdt;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

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