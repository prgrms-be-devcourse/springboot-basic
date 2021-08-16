package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.order.OrderItem;
import com.programmers.kdtspringorder.order.OrderService;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        VoucherRepository voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");

        OrderService orderService = applicationContext.getBean(OrderService.class);

        Voucher voucher = voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));


        var customerId = UUID.randomUUID();
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());


        applicationContext.close();
//        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount '{'0'}' is not 90L", order.totalAmount()));

    }
}
