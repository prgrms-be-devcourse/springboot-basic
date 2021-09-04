package org.prgrms.kdt;

import org.prgrms.kdt.order.domain.Order;
import org.prgrms.kdt.order.domain.OrderItem;
import org.prgrms.kdt.order.service.OrderService;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) throws IOException {
        var customerId = UUID.randomUUID();
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var orderService = applicationContext.getBean(OrderService.class);
        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher = voucherRepository.create(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        Order order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());
        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));
    }
}
