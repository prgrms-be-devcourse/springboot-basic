package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    // 실제 로그를 남기는것은 로그백 프레임워크니까 로그백설정을 통해 로깅설정을 할 수가 있음

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) throws Throwable {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        // xml이 아닌 java클래스 기반의 application config를 사용할때는 어노테이션컨피크어플리케이션 컨텍스트를 사용한다. 인터페이스를 구현한 구현클래스
        // 인자로 빈 설정을 담고있는 메타데이터 클래스를 준다.
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        // 수업진행부분
//        var environment = applicationContext.getEnvironment();
//        var version = environment.getProperty("kdt.version");
//        var minimunOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
//        var supportVendors = environment.getProperty("kdt.support-vendors", List.class);
//        var description = environment.getProperty("kdt.description", List.class);
//        System.out.println(MessageFormat.format("version -> {0}", version));
//        System.out.println(MessageFormat.format("minimunOrderAmount -> {0}", minimunOrderAmount));
//        System.out.println(MessageFormat.format("description -> {0}", supportVendors));
//        System.out.println(MessageFormat.format("description -> {0}", description));

        OrderProperties orderProperties = applicationContext.getBean(OrderProperties.class);
        logger.info("logger name -> {}",logger.getName());

        logger.info(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
        logger.info(MessageFormat.format("minimunOrderAmount -> {0}", orderProperties.getMinimunOrderAmount()));
        logger.info(MessageFormat.format("description -> {0}", orderProperties.getSupportVendors()));
        logger.info(MessageFormat.format("description -> {0}", orderProperties.getDescription()));


        var customerId = UUID.randomUUID();

        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
        var voucher = voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            // 100원짜리 한개를 들고있도록함
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));
    }
}
