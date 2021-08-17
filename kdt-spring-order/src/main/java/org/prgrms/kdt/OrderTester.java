package org.prgrms.kdt;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.Annotation;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var customerId = UUID.randomUUID();
        var applicationContext =  new AnnotationConfigApplicationContext(AppConfiguration.class);
        //var orderContext = new AppConfiguration();
        var orderService = applicationContext.getBean(OrderService.class);

        //var orderService = orderContext.orderService();
        var order = orderService.createOrder(customerId,new ArrayList<OrderItem>(){{
            add(new OrderItem(UUID.randomUUID(),100L,1));
        }});


//        var orderItems = new ArrayList<OrderItem>() {{
//            add(new OrderItem(UUID.randomUUID(),100L,1));
//        }};
        // 단순히 100원에서 10원을 뺄거고 이 10원은 fixedAmount를 통해 하드코딩 되어있다.
        // 의존 관계가 order -> FixedAmountVoucher 로 FAV를 수정하면 order의 로직이 변하게 된다.
        // 강한 결합도
        //var order = new Order(UUID.randomUUID(),customerId,orderItems,10L);

        //일반적인 제어의 구조 : order에서 모든 기능을 선택해서 사용
        //var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),10L);
        //var order = new Order(UUID.randomUUID(),customerId,orderItems,fixedAmountVoucher);

        //제어의 역전, 라이브러리와 다르게 프레임워크에서
        //우리는 기능을 만들고 프레임워크가 이것을 실행하게 만든다.


        Assert.isTrue(order.totalAmount()==100L, MessageFormat.format("totalAmount{0} is not 100L", order.totalAmount()));
    }

}
