package org.prgrms.kdt.kdtspringorder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest
class KdtSpringOrderApplicationTests {

    @Test
    void contextLoadsTest() {
    }

//    @Test
//    void looseCoupling() {
//
//        UUID customerId = UUID.randomUUID();
//        ArrayList<OrderItem> orderItems = new ArrayList<>(){{
//            add(new OrderItem(UUID.randomUUID(), 100L, 1));
//        }};
//
//        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
//        Order order = new Order(UUID.randomUUID(), customerId, orderItems, fixedAmountVoucher); // 외부에서 할인 정책을 주입
//
//        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));
//
//    }

    @Test
    void orderContextTest() {

        UUID customerId = UUID.randomUUID();
        OrderContext orderContext = new OrderContext();
        OrderService orderService = orderContext.orderService();

        Order order = orderService.createOrder(customerId, new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }});

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));

    }

    @Test
    void AppConfigurationTest() {

        AnnotationConfigApplicationContext applocationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        UUID customerId = UUID.randomUUID();
        OrderService orderService = applocationContext.getBean(OrderService.class);

        Order order = orderService.createOrder(customerId, new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }});

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));

    }
}
