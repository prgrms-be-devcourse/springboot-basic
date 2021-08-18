package org.prgrms.kdt;

import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        UUID customerId = UUID.randomUUID();
        ArrayList<OrderItem> orderItems = new ArrayList<>(Arrays.asList(new OrderItem(UUID.randomUUID(), 100, 1)));

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Order order = new Order(UUID.randomUUID(), customerId, orderItems, fixedAmountVoucher);
        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));

    }
}
