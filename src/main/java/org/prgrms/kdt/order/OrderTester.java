package org.prgrms.kdt.order;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.order.service.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(final String[] args) throws IOException {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        final var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        final var orderProperties = applicationContext.getBean(OrderProperties.class);
        logger.info("logger name => {}", logger.getName());
        logger.info("version -> {}", orderProperties.getVersion());
        logger.info("minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
        logger.info("supportVendors -> {}", orderProperties.getSupportVendors());
        logger.info("description -> {}", orderProperties.getDescription());

        final var customerId = UUID.randomUUID();
        final var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
        final var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        final var orderService = applicationContext.getBean(OrderService.class);
        final var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));

        applicationContext.close();
    }
}