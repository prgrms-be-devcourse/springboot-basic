package org.prgrms.kdtspringw1d1.order;

import org.prgrms.kdtspringw1d1.config.AppConfiguration;
import org.prgrms.kdtspringw1d1.voucher.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var orderService = applicationContext.getBean(OrderService.class);

        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher = voucherRepository.createFixedAmountVoucher();

        var order = orderService.createOrder(UUID.randomUUID(), new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
    }
}
