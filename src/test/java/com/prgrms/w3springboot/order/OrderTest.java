package com.prgrms.w3springboot.order;

import com.prgrms.w3springboot.configuration.AppConfig;
import com.prgrms.w3springboot.order.service.OrderService;
import com.prgrms.w3springboot.voucher.FixedAmountVoucher;
import com.prgrms.w3springboot.voucher.PercentAmountVoucher;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

class OrderTest {

    @Test
    void 고정할인적용() {
        var customerId = UUID.randomUUID();
        var orderItems = List.of(
                new OrderItem(UUID.randomUUID(), 100L, 1)
        );
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, fixedAmountVoucher);

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount is not {0}", order.totalAmount()));
    }

    @Test
    void 퍼센트할인적용() {
        var customerId = UUID.randomUUID();
        var orderItems = List.of(
                new OrderItem(UUID.randomUUID(), 100L, 1)
        );
        var percentAmountVoucher = new PercentAmountVoucher(UUID.randomUUID(), 200L);
        var order = new Order(UUID.randomUUID(), customerId, orderItems, percentAmountVoucher);

        Assert.isTrue(order.totalAmount() == 200L, MessageFormat.format("totalAmount is not {0}", order.totalAmount()));
    }

    @Test
    void 주문생성_의존성주입() {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(UUID.randomUUID(), List.of(
                new OrderItem(UUID.randomUUID(), 100L, 1))
        );

        Assert.isTrue(order.totalAmount() == 100L, MessageFormat.format("totalAmount is not {0}", order.totalAmount()));
    }

}