package org.prgrms.kdt.order;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.order.service.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(final String[] args) {
        final var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

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