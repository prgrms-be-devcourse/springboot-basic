package org.prgrms.kdt;

import org.apache.logging.log4j.message.Message;
import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.service.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderTester {

    public static void main(String[] args) {
        // Spring Application Context를 만드는데 Java 기반껄 만들꺼야
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        // env 가져옵시다.
        var environment = applicationContext.getEnvironment();
        var version = environment.getProperty("kdt.version");
        var minimumOrderAmount = environment.getProperty("kdt.minium-order-amount", Integer.class);
        var supportVendeors = environment.getProperty("kdt.support-vendors", List.class);
        System.out.println(MessageFormat.format("version -> {0}", version));
        System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", minimumOrderAmount));
        System.out.println(MessageFormat.format("supportVendeors -> {0}", supportVendeors));

        var customerId = UUID.randomUUID();

        // bean에서 꺼내올대도 BeanFactoryAnnotationUtils에서 적합한 용도를 가진 Bean을 선택할 수 있습니다.
        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
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