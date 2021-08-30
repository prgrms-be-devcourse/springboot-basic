package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.order.Order;
import com.programmers.kdtspringorder.order.OrderItem;
import com.programmers.kdtspringorder.order.OrderProperties;
import com.programmers.kdtspringorder.order.OrderService;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.repository.JdbcVoucherRepository;
import com.programmers.kdtspringorder.voucher.repository.MemoryVoucherRepository;
import com.programmers.kdtspringorder.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.boot.ansi.AnsiOutput;
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

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) throws IOException {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("dev");
        applicationContext.refresh();

        String version = environment.getProperty("kdt.version");
        Integer minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
        List suportsVenderList = environment.getProperty("kdt.support-vendors", List.class);
        String description = environment.getProperty("kdt.description");
//
        OrderProperties orderProperties = applicationContext.getBean(OrderProperties.class);

        System.out.println(MessageFormat.format("version = {0}", orderProperties.getVersion()));
        System.out.println(MessageFormat.format("minimumOrderAmount = {0}", orderProperties.getMinimumOrderAmount()));
        System.out.println(MessageFormat.format("supportVendorList = {0}", orderProperties.getSupportVendors()));
        System.out.println(MessageFormat.format("description = {0}", orderProperties.getDescription()));


        logger.info("version -> {}", orderProperties.getVersion());
        logger.info("minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
        logger.info("supportVendors -> {}", orderProperties.getSupportVendors());
        logger.info("description -> {}", orderProperties.getDescription());



//        System.out.println("orderProperties.getSupportVendors() = " + orderProperties.getSupportVendors());

//        Resource resource = applicationContext.getResource("application.yaml");
//        Resource resource2 = applicationContext.getResource("file:sample.txt");
//        Resource resource3 = applicationContext.getResource("https://stackoverflow.com/");
//        ReadableByteChannel readableByteChannel = Channels.newChannel(resource3.getURL().openStream());
//
//        BufferedReader bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
//        String collect = bufferedReader.lines().collect(Collectors.joining("\n"));
//        System.out.println(collect);

//        System.out.println(resource2.getClass().getCanonicalName());
//        File file = resource2.getFile();
//        List<String> strings = Files.readAllLines(file.toPath());
//        List<String> strings2 = Files.readAllLines(file.toPath());
//        System.out.println(strings.stream().reduce("",(a,b)->a+"\n" + b));
//        System.out.println(strings2.stream().reduce("",(a,b)->a+"\n" + b));

//        VoucherRepository voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
        VoucherRepository voucherRepository = applicationContext.getBean(JdbcVoucherRepository.class);
        OrderService orderService = applicationContext.getBean(OrderService.class);
//        Voucher voucher = voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository instanceof JdbcVoucherRepository));
        System.out.println(MessageFormat.format("is Jdbc Repo ->{0}", voucherRepository.getClass().getCanonicalName()));

        applicationContext.close();
    }
}
