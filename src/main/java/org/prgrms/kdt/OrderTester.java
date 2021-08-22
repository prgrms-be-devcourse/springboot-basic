package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderTester {
    public static void main(String[] args) {
//        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

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
