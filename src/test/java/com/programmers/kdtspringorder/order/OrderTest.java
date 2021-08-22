package com.programmers.kdtspringorder.order;

import com.programmers.kdtspringorder.order.Order;
import com.programmers.kdtspringorder.order.OrderItem;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

class OrderTest {

    @Test
    void totalAmount() {
        // given
        var orderItems = new ArrayList<OrderItem>(){{
            add(new OrderItem(UUID.randomUUID(), 100L, 2));
        }};

        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), orderItems, voucher);

        // when
        long amount = order.totalAmount();

        // then
        Assertions.assertThat(amount).isEqualTo(180L);
    }
}