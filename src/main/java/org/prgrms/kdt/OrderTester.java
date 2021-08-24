package org.prgrms.kdt;

import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.engine.order.OrderItem;
import org.prgrms.kdt.engine.order.OrderProperties;
import org.prgrms.kdt.engine.order.OrderService;
import org.prgrms.kdt.engine.voucher.FixedAmountVoucher;
import org.prgrms.kdt.engine.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderTester {
    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) throws IOException {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        var environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("prod");
        applicationContext.refresh();

        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        logger.info("logger name => {}", logger.getName());
        logger.info("repository -> {}", voucherRepository.getClass());

        var orderService = applicationContext.getBean(OrderService.class);
        var customerId = UUID.randomUUID();
        var orderProperties = applicationContext.getBean(OrderProperties.class);
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        applicationContext.close();
        Assert.isTrue(order.totalAmount() == 90L, "totalAmount " + order.totalAmount() + " is not 90L");

//        var environment = applicationContext.getEnvironment();
//        var version = environment.getProperty("version");
//        System.out.println(version);
//        System.out.println(orderProperties.getVersion());
//        System.out.println(orderProperties.getSupportVendors());
//        var resource2 = applicationContext.getResource("file:src/test/sample.txt");
//        var resource3 = applicationContext.getResource("https://stackoverflow.com/");
//        System.out.println(MessageFormat.format("Resource -> {0}", resource3.getClass().getCanonicalName()));
//        var readableByteChannel = Channels.newChannel(resource3.getURL().openStream());
//        var bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
//        var contents = bufferedReader.lines().collect(Collectors.joining("\n"));
//        System.out.println(contents);
//        var file = resource2.getFile();
//        var strings = Files.readAllLines(file.toPath());
//        System.out.println(strings.stream().reduce("", (a, b) -> a + "\n" + b));;


    }
}
