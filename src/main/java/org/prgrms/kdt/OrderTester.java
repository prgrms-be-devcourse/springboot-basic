package org.prgrms.kdt;

import org.prgrms.kdt.Model.OrderItem;
import org.prgrms.kdt.Service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {

        System.out.println("=== Voucher Program ===");
        Scanner scanner = new Scanner(System.in);
        String cmd=scanner.nextLine();

        if (cmd.equals("create")){

        }
        else if(cmd.equals("list")){

        }
        else if(cmd.equals("exit")) {

            //자바 기반의 메타데이터 설정은 어노테이션
            var apllicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
            var customerId = UUID.randomUUID();
            var orderservice = apllicationContext.getBean(OrderService.class);
            var order = orderservice.createOrder(customerId, new ArrayList<OrderItem>() {{
                add(new OrderItem(UUID.randomUUID(), 100L, 1));
            }});
            Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
        }


    }
}
