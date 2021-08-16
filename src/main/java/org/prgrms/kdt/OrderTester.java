package org.prgrms.kdt;

import org.prgrms.kdt.ValueObject.OrderItem;
import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    public static void main(String[] args) {
        // xml이 아닌 java클래스 기반의 application config를 사용할때는 어노테이
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var customerId = UUID.randomUUID();
        // IoC컨테이너로부터 빈으로 등록된 orderSerivce를 기져옴
        OrderService orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(UUID.randomUUID(), new ArrayList<>() {{
            // 100원짜리 한개를 들고있도록함
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }});
        Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));
    }
}
