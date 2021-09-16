package org.prgrms.kdt;

import org.apache.logging.log4j.message.Message;
import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.property.OrderProperties;
import org.prgrms.kdt.order.service.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderTester {

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) throws IOException {
        // Spring Application Context를 만드는데 Java 기반껄 만들꺼야
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);// Color 바꾸기
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        // env 가져옵시다.
//        var environment = applicationContext.getEnvironment();
//        environment.setActiveProfiles("local");
        // applicationContext.refresh();
//        var version = environment.getProperty("kdt.version");
//        var minimumOrderAmount = environment.getProperty("kdt.minium-order-amount", Integer.class);
//        var supportVendeors = environment.getProperty("kdt.support-vendors", List.class);
//        var description = environment.getProperty("kdt.description");


        var orderProperties = applicationContext.getBean(OrderProperties.class);
        logger.error("logger name => {}", logger.getName()); // logger도 치환이 됨
        logger.warn("version -> {}", orderProperties.getVersion());
        logger.info("minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
        logger.info("supportVendeors -> {}", orderProperties.getSupportVendors());
        logger.info("description -> {}", orderProperties.getDescription());

        // 리소스 가져오기
        // 클레스 path에서 가져오기.
        // 이런식으로 spring은 resource를 통해 classpath, file, http(s) 리소스등을 쉽게 가져올수 있는 interface를 제공해주며 이것들은 다 applicatinContext에서 가져옴.
        // 이걸 통해 우리가 실제 리소스를 가져오기 위한 구현체를 알 필요 없이 Spring의 철학인 DI 에 의해 원하는 리소스가 알아서 인터페이스를 통해 일관된 방법을 제공해줌. 굳굳.
        var resource = applicationContext.getResource("classpath:application.yaml"); // classPathContextResource
        var resource2 = applicationContext.getResource("file:sample/sample.txt"); // working dir 기준
        var resource3 = applicationContext.getResource("https://stackoverflow.com/"); // html file : UrlResource

        System.out.println(MessageFormat.format("Resource -> {0}", resource.getClass().getCanonicalName()));
        System.out.println(MessageFormat.format("Resource2 -> {0}", resource2.getClass().getCanonicalName()));

        var strings = Files.readAllLines(resource.getFile().toPath());
        System.out.println(strings.stream().reduce("", (a, b) -> a + "\n" + b));
        var strings2 = Files.readAllLines(resource2.getFile().toPath());
        System.out.println(strings2.stream().reduce("", (a, b) -> a + "\n" + b));

        var readableByteChannel= Channels.newChannel(resource3.getURL().openStream()); // 실제 다운받게헤줘야함.
        var bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
        var contents = bufferedReader.lines().collect(Collectors.joining());
        // System.out.println(contents);

        var customerId = UUID.randomUUID();

        // bean에서 꺼내올대도 BeanFactoryAnnotationUtils에서 적합한 용도를 가진 Bean을 선택할 수 있습니다.
        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        // System.out.println(MessageFormat.format("voucher -> {0}", voucherRepository.find()));


        var orderService = applicationContext.getBean(OrderService.class); // 대박 ㅋㅋ
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));

        // ApplicationContext 소멸
        /**
         * Bean 생성 생명주기 콜백
         * 1. @PostConstruct 가 적영된 메소드 호출
         * 2. Bean이 InitializingBean 인터페이스 구현시 afterPropertiesSet 호출
         * 3. @Bean Annotation의 initMethod에 설정한 메소드 호출
         *
         * Bean 소멸 생명주기 콜백
         * 1. @PreDestroy 가 적용된 메소드 호출
         * 2. Bean 이 DisposableBean 인터페이스 구현시 destroy 호출
         * 3. @Bean 의 destroyMethod 에 설정한 메소드 호출
         */
        applicationContext.close(); // destory는 deprecated
    }
}