package org.prgrms.kdt.test;

import org.prgrms.kdt.entity.OrderItem;
import org.prgrms.kdt.configure.AppConfiguration;
import org.prgrms.kdt.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {

//        var customerId = UUID.randomUUID();
//        var orderItems = new ArrayList<OrderItem>(){{
//            add(new OrderItem( UUID.randomUUID(), 100L, 1));
//        }};
//        //order 생성
//        var order = new Order(UUID.randomUUID(),customerId,orderItems,
//                new FixedAmountVoucher(UUID.randomUUID(), 10L));
//        //검증하기
//        Assert.isTrue(order.totalAmount() == 90L,
//                MessageFormat.format("totalAmount({0}) is not 90L" , order.totalAmount()));


        //configuration으로 빈으로 등록하기 전!
//        var orderContext = new AppConfiguration();
//        var orderService = orderContext.orderService();

        //configuration으로 Bean 등록 후 사용
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(UUID.randomUUID(), new ArrayList<>(){{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, UUID.randomUUID());
        Assert.isTrue(order.totalAmount() == 90L,
        MessageFormat.format("totalAmount({0}) is not 90L" , order.totalAmount()));

    }
}
