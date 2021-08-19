package org.prgms.order;

import org.prgms.AppConfiguration;
import org.prgms.voucher.FixedAmountVoucher;
import org.prgms.voucher.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var customerId = UUID.randomUUID();

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var voucherRepository = applicationContext.getBean(VoucherRepository.class);

        var voucher = voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(),10L));

        var orderService = applicationContext.getBean(OrderService.class);

        var order = orderService.createOrder(customerId,  new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }},  voucher.getVoucherId());
        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
    }
}
