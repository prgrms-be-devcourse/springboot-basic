package org.prgrms.kdt;

import org.prgrms.kdt.common.config.AppConfiguration;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
        var voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
        System.out.println(MessageFormat.format("voucherRepository == voucherRepository2 => {0}",voucherRepository ==  voucherRepository2));
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        var customerId = UUID.randomUUID();
        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>(){{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }},voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("total Amount {0} is not 90L", order.totalAmount()));

        applicationContext.close();
    }
}
