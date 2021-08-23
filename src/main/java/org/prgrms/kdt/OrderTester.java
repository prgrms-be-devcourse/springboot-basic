package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.VoucherRepository;
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

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) {
//        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);

        // profile 예제
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        var environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("dev");
        applicationContext.refresh();

//        var voucherRepository = BeanFactoryAnnotationUtils
//                .qualifiedBeanOfType(applicationContext.getBeanFactory(),
//                        VoucherRepository.class,
//                        "memory");
        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var orderService = applicationContext.getBean(OrderService.class);

        var customerId = UUID.randomUUID();
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        var order = orderService.createOrder(customerId,
                new ArrayList<>() {{
                    add(new OrderItem(UUID.randomUUID(), 100L, 1));
                }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount{0} is not 90L", order.totalAmount()));

        applicationContext.close();
    }
}
