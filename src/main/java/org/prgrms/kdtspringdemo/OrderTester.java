package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.order.OrderItem;
import org.prgrms.kdtspringdemo.order.OrderService;
import org.prgrms.kdtspringdemo.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        var application = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var customerId = UUID.randomUUID();
        var voucherRepository = application.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        var orderService = application.getBean(OrderService.class);

        var order = orderService.createOrder(customerId, new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L,  MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));
    }
}
