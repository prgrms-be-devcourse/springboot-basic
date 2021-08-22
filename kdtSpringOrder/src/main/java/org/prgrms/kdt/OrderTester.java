package org.prgrms.kdt;

import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

@EnableAutoConfiguration
public class OrderTester {

    //Logger는 클래스 레벨에서 만든다.
    private static final Logger logger  = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        var environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("prod");
        applicationContext.refresh();

        logger.warn("logger name => {}", logger.getName());

        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher= voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        var orderService = applicationContext.getBean(OrderService.class);

        var customerId = UUID.randomUUID();
        var order = orderService.createOrder(customerId, new ArrayList<>(){{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount{0} is not 90L", order.totalAmount()));
    }

}