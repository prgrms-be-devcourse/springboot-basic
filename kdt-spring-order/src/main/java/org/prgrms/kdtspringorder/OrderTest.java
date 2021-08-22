package org.prgrms.kdtspringorder.order;

import org.prgrms.kdtspringorder.AppConfiguration;
import org.prgrms.kdtspringorder.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringorder.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class OrderTest {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var customerId  = randomUUID();
        //var voucherRepository =applicationContext.getBean(VoucherRepository.class);
        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(),VoucherRepository.class,"memory");
        var voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(),VoucherRepository.class,"memory");
        System.out.println(MessageFormat.format("voucherRepository {0}",voucherRepository));
        System.out.println(MessageFormat.format("voucherRepository2 {0}",voucherRepository));
        System.out.println(MessageFormat.format("voucherRepository == voucherRepository2 => {0}", voucherRepository == voucherRepository2));
        var voucher =voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),10L));
        var orderService =applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>(){{
            add(new OrderItem(randomUUID(),100L,1));
        }},voucher.getVoucherId());
        Assert.isTrue(order.totalAmount() == 90L, MessageFormat
                .format("totalAmount {0} is not 100L", order.totalAmount()));

        applicationContext.close();

    }
}
