package com.prgrms.model.voucher;

import com.prgrms.model.order.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {
    @Test
    public void salePriceBiggerThanDiscountTest() {
        UUID voucherId = UUID.randomUUID();
        Voucher createdVoucher = new PercentDiscountVoucher(voucherId, new Discount(20), VoucherPolicy.FixedAmountVoucher);
        OrderItem orderItem = new OrderItem(UUID.randomUUID(), 1000, 1);
        long price = 1000;

        assertEquals(800, createdVoucher.getRealPrice(orderItem));
    }
}