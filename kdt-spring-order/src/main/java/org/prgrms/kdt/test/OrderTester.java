package org.prgrms.kdt.test;

import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.OrderItem;
import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.domain.OrderProperties;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OrderTester {
    public static void main(String[] args) throws IOException {

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
//        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
//        var orderService = applicationContext.getBean(OrderService.class);
//        var order = orderService.createOrder(UUID.randomUUID(), new ArrayList<>(){{
//            add(new OrderItem(UUID.randomUUID(), 100L, 1));
//        }}, UUID.randomUUID());
//        Assert.isTrue(order.totalAmount() == 90L,
//        MessageFormat.format("totalAmount({0}) is not 90L" , order.totalAmount()));

        //3일차 강의 -> ComponentScan으로 찾기
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

//        var environment = applicationContext.getEnvironment();
//        var version = environment.getProperty("kdt.version");
//        var minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount",Integer.class);
//        var supportVendors = environment.getProperty("kdt.support-vendors", List.class);
//        var description = environment.getProperty("kdt.description", List.class);

        //properties 주입 받아 출력
        var orderProperties = applicationContext.getBean(OrderProperties.class);
        System.out.println(MessageFormat.format("version -> {0}",orderProperties.getVersion()));
        System.out.println(MessageFormat.format("minimumOrderAmount -> {0}",orderProperties.getMinimumOrderAmount()));
        System.out.println(MessageFormat.format("supportVendors -> {0}",orderProperties.getSupportVendors()));
        System.out.println(MessageFormat.format("description -> {0}",orderProperties.getDescription()));


        //resource 이용하여 파일 읽기
        var resource = applicationContext.getResource("file:voucher_file.txt");
        var strings = Files.readAllLines(resource.getFile().toPath());
        System.out.println(strings.stream().reduce("",(a,b)->a+"\n"+b));


        var customerId = UUID.randomUUID();
        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert2(new FixedAmountVoucher(UUID.randomUUID(),10L));
        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(customerId,new ArrayList<>(){{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L ,
                MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));

        applicationContext.close();

    }
}
