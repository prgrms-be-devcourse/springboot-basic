package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.order.OrderItem;
import org.prgrms.kdtspringdemo.order.OrderService;
import org.prgrms.kdtspringdemo.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var application = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var environment = application.getEnvironment();
        var version = environment.getProperty("kdt.version");
        var minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
        var supportVendors = environment.getProperty("kdt.support-vendors", List.class);
        System.out.println(MessageFormat.format("version -> {0}", version));
        System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", minimumOrderAmount));
        System.out.println(MessageFormat.format("supportVendors -> {0}", supportVendors));


        var customerId = UUID.randomUUID();

        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(application.getBeanFactory(), VoucherRepository.class, "memory");
        var voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(application.getBeanFactory(), VoucherRepository.class, "memory");
        System.out.println("voucherRepo : " + voucherRepository);
        System.out.println("voucherRepo : " + voucherRepository2);
        System.out.println("is same? : " + (voucherRepository == voucherRepository2));
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        var orderService = application.getBean(OrderService.class);

        var order = orderService.createOrder(customerId, new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L,  MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));
    }
}
