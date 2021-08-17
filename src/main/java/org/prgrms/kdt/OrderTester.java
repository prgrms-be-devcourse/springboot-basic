package org.prgrms.kdt;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;
import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 7:50 오후
 */
public class OrderTester {

    private final OrderService orderService;

    public OrderTester(OrderService orderService) {
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(UUID.randomUUID(), new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100, 1));
        }}, UUID.randomUUID());
        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
    }

}
