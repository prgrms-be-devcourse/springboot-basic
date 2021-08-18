package org.prgrms.kdt;

import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    public static void main(String[] args) {
        //현업에서 var를 쓰는건 컨벤션을 따를까?
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher= voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        var orderService = applicationContext.getBean(OrderService.class);

        var customerId = UUID.randomUUID();
        var order = orderService.createOrder(customerId, new ArrayList<>(){{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount{0} is not 90L", order.totalAmount()));
    }

}