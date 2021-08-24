package com.prgrms.devkdtorder;

import com.prgrms.devkdtorder.cla.CommandLineApplication;
import com.prgrms.devkdtorder.config.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderTester {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

//        var customerId = UUID.randomUUID();
//        var orderItems = new ArrayList<OrderItem>() {{
//            add(new OrderItem(UUID.randomUUID(), 100L, 1));
//        }};
//
//        OrderService orderService = applicationContext.getBean(OrderService.class);
//        Order order = orderService.createOrder(customerId, orderItems);
//
//        Assert.isTrue(order.totalAmount() == 90L, "totalAmount "+order.totalAmount());

        CommandLineApplication bean = applicationContext.getBean(CommandLineApplication.class);
        bean.run();
    }
}
