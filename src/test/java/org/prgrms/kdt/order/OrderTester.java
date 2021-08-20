package org.prgrms.kdt.order;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.order.service.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    public static void main(String[] args) {
        // Spring Application Context를 만드는데 Java 기반껄 만들꺼야
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var customerId = UUID.randomUUID();

        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        var orderService = applicationContext.getBean(OrderService.class); // 대박 ㅋㅋ
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
    }
}