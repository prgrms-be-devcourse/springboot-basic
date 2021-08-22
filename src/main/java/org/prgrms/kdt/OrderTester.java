package org.prgrms.kdt;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 7:50 오후
 */
public class OrderTester {

    private final OrderService orderService;

    public OrderTester(OrderService orderService) {
        this.orderService = orderService;
    }

    // 수업 내용
    public static void main(String[] args) throws IOException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        // getEnvironment를 통해 프로퍼티를 확인하는 방법
        printEnvironmentProperties(applicationContext);

        // 빈으로 주입된 프로퍼티 객체를 통해 프로퍼티를 확인하는 방법
        printProperties(applicationContext);

        // 리소스에 접근하는 방법
        printResource(applicationContext);

        // 빈이 싱글톤인지 확인
        VoucherRepository voucherRepository = getVoucherRepository(applicationContext);

        // 주입된 빈으로 주문이 작동하는지 확인
        checkOrderByInjectBean(applicationContext, voucherRepository);

        applicationContext.close();
    }

    private static void printEnvironmentProperties(AnnotationConfigApplicationContext applicationContext) {
        var environment = applicationContext.getEnvironment();
        var version = environment.getProperty("kdt.version");
        var minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
        var supportVendors = environment.getProperty("kdt.support-vendors", List.class);
        var description = environment.getProperty("kdt.description", List.class);

        System.out.println(MessageFormat.format("version = {0}", version));
        System.out.println(MessageFormat.format("minimumOrderAmount = {0}", minimumOrderAmount));
        System.out.println(MessageFormat.format("supportVendors = {0}", supportVendors));
        System.out.println(MessageFormat.format("description = {0}", description));
    }

    private static void printProperties(AnnotationConfigApplicationContext applicationContext) {
        var orderProperties = applicationContext.getBean(OrderProperties.class);
        System.out.println(MessageFormat.format("version = {0}", orderProperties.getVersion()));
        System.out.println(MessageFormat.format("minimumOrderAmount = {0}", orderProperties.getMinimumOrderAmount()));
        System.out.println(MessageFormat.format("supportVendors = {0}", orderProperties.getSupportVendors()));
        System.out.println(MessageFormat.format("description = {0}", orderProperties.getDescription()));
    }

    private static void printResource(AnnotationConfigApplicationContext applicationContext)
            throws IOException {
        var resource = applicationContext.getResource("classpath:application.yml");
        var resource2 = applicationContext.getResource("file:test/sample.txt");
        var resource3 = applicationContext.getResource("https://stackoverflow.com");

        System.out.println(MessageFormat.format("resource -> {0}", resource.getClass().getCanonicalName()));
        System.out.println(MessageFormat.format("resource -> {0}", resource2.getClass().getCanonicalName()));
        System.out.println(MessageFormat.format("resource -> {0}", resource3.getClass().getCanonicalName()));

        var strings = Files.readAllLines(resource.getFile().toPath());
        System.out.println(strings.stream().reduce("", (a, b) -> a + "\n" + b));

        var textFile = Files.readAllLines(resource2.getFile().toPath());
        System.out.println(textFile.stream().reduce("", (a, b) -> a + "\n" + b));

        var readableByteChannel = Channels.newChannel(resource3.getURL().openStream());
        var bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
        System.out.println(bufferedReader.lines().collect(Collectors.joining("\n")));
    }

    private static VoucherRepository getVoucherRepository(
            AnnotationConfigApplicationContext applicationContext) {
        var voucherRepository = BeanFactoryAnnotationUtils
                .qualifiedBeanOfType(applicationContext.getBeanFactory(),
                        VoucherRepository.class, "memory");

        var voucherRepository2 = BeanFactoryAnnotationUtils
                .qualifiedBeanOfType(applicationContext.getBeanFactory(),
                        VoucherRepository.class, "memory");

        System.out.println(MessageFormat.format("voucherRepository {0}", voucherRepository));
        System.out.println(MessageFormat.format("voucherRepository2 {0}", voucherRepository2));
        System.out.println(MessageFormat.format("voucherRepository == voucherRepository => {0}", voucherRepository == voucherRepository2));
        return voucherRepository;
    }


    private static void checkOrderByInjectBean(AnnotationConfigApplicationContext applicationContext, VoucherRepository voucherRepository) {
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(UUID.randomUUID(), new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100, 1));
        }}, voucher.getVoucherId());
        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
    }
}
