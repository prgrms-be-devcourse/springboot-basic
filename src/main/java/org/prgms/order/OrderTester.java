package org.prgms.order;

import org.prgms.order.order.OrderItem;
import org.prgms.order.order.OrderService;
import org.prgms.order.voucher.FixedAmountVoucher;
import org.prgms.order.voucher.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var customerId = UUID.randomUUID();

        var voucherRepository = applicationContext.getBean(VoucherRepository.class); //변수에서는 구현체를 알수가 없다.
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        var orderService = applicationContext.getBean(OrderService.class);

        var order = orderService.createOrder(customerId,
                new ArrayList<OrderItem>(){{
                    add(new OrderItem(UUID.randomUUID(), 100L,1));
                }},
                voucher.getVoucherId()
        );

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L ",order.totalAmount()));

    }
}
