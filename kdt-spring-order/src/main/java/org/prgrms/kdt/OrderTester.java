package org.prgrms.kdt;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {

        // 객체 생성을 대신하는 클래스 (IoC, 팩토리)
        //var orderContext = new AppConfiguration();
        // 객체 생성도 대신해줌
        //var orderService = orderContext.orderService();


        // 사용자 ID 생성
        var customerId = UUID.randomUUID();
        // App-context에 등록
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var orderService = applicationContext.getBean(OrderService.class);

        var order = orderService.createOrder(
                customerId,
                new ArrayList<OrderItem>(){{
                    add(new OrderItem(UUID.randomUUID(), 100L, 1));
                }}
        );

        // 거짓이라면 오류메시지 출력
        Assert.isTrue(
                order.totalAmount() == 100L,
                MessageFormat.format("totalAmount{0} is not 90L", order.totalAmount())
        );
    }
}
