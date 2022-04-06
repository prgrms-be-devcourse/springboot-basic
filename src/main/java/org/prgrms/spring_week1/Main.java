package org.prgrms.spring_week1;

import org.prgrms.spring_week1.models.Order;
import org.prgrms.spring_week1.models.OrderItem;
import org.prgrms.spring_week1.models.Voucher;
import org.prgrms.spring_week1.services.OrderService;
import org.prgrms.spring_week1.services.VoucherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        UUID customerId = UUID.randomUUID();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(UUID.randomUUID(),100L, 1));

        VoucherService voucherService = ac.getBean(VoucherService.class);
        Voucher voucher = voucherService.createFixedVoucher(10L);
        OrderService orderService = ac.getBean(OrderService.class);

        Order order = orderService.createOrder(customerId, orderItems, voucher);
        Assert.isTrue(order.totalPrice() == 90L);

    }
}
