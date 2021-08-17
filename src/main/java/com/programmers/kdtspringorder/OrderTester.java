package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.order.Order;
import com.programmers.kdtspringorder.order.OrderItem;
import com.programmers.kdtspringorder.order.OrderProperties;
import com.programmers.kdtspringorder.order.OrderService;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.repository.MemoryVoucherRepository;
import com.programmers.kdtspringorder.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderTester {
    public static void main(String[] args) throws IOException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

//        ConfigurableEnvironment environment = applicationContext.getEnvironment();
//        String version = environment.getProperty("kdt.version");
//        Integer minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
//        List suportsVenderList = environment.getProperty("kdt.support-vendors", List.class);
//        String description = environment.getProperty("kdt.description");
//
//        System.out.println(MessageFormat.format("version = {0}", version));
//        System.out.println(MessageFormat.format("minimumOrderAmount = {0}", minimumOrderAmount));
//        System.out.println(MessageFormat.format("supportVendorList = {0}", suportsVenderList));
//        System.out.println(MessageFormat.format("description = {0}", description));

//        OrderProperties orderProperties = applicationContext.getBean(OrderProperties.class);
//        System.out.println("orderProperties.getSupportVendors() = " + orderProperties.getSupportVendors());

        Resource resource = applicationContext.getResource("application.yaml");
        Resource resource2 = applicationContext.getResource("file:sample.txt");
        Resource resource3 = applicationContext.getResource("https://stackoverflow.com/");
        ReadableByteChannel readableByteChannel = Channels.newChannel(resource3.getURL().openStream());

        BufferedReader bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
        String collect = bufferedReader.lines().collect(Collectors.joining("\n"));
        System.out.println(collect);

//        System.out.println(resource2.getClass().getCanonicalName());
//        File file = resource2.getFile();
//        List<String> strings = Files.readAllLines(file.toPath());
//        List<String> strings2 = Files.readAllLines(file.toPath());
//        System.out.println(strings.stream().reduce("",(a,b)->a+"\n" + b));
//        System.out.println(strings2.stream().reduce("",(a,b)->a+"\n" + b));

//        VoucherRepository voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
//        OrderService orderService = applicationContext.getBean(OrderService.class);
//
//        Voucher voucher = voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));

//        var customerId = UUID.randomUUID();
//        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
//            add(new OrderItem(UUID.randomUUID(), 100L, 1));
//        }}, voucher.getVoucherId());
//
//        ArrayList<OrderItem> orderItems = new ArrayList<>();


//
//        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount '{'0'}' is not 90L", order.totalAmount()));

        applicationContext.close();
    }
}
