package org.prms.kdtordertest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import javax.security.auth.login.AppConfigurationEntry;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    public static void main(String[] args) {

        // AppConfiguration을 사용하기 위해
        var applicationContext=new AnnotationConfigApplicationContext(AppConfiguration.class);

        var customerId= UUID.randomUUID();

        // AppConfiguration에 등록된 메소드를 사용하기 위한 orderService
        var orderService=applicationContext.getBean(OrderService.class);

        var orderContext=new AppConfiguration();
//        var orderService =orderContext.orderService(orderService1,orderContext.orderRepository());
        var order=orderService.createOrder(customerId,new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(),100L,1));
        }});

        Assert.isTrue(order.totalAmount()==100L, MessageFormat.format("totalAMount {0} is not 90L",order.totalAmount()));


    }
}
