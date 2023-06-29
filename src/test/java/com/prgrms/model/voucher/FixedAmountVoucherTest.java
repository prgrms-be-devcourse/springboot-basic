package com.prgrms.model.voucher;

import com.prgrms.controller.VoucherController;
import com.prgrms.model.order.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    OrderItem orderItem;

    @Test
    @DisplayName("할인된 가격이 양수가 나오는 상황을 테스트합니다.")
    public void getPricePositiveResult() {
        orderItem = new OrderItem(UUID.randomUUID(), 1000, 1);
        UUID voucherId = UUID.randomUUID();
        Voucher createdVoucher = new FixedAmountVoucher(voucherId, new Discount(20), VoucherPolicy.FixedAmountVoucher);


        assertEquals(980, createdVoucher.getRealPrice(orderItem));
    }

    @Test
    @DisplayName("할인된 가격이 음수가 나오는 상황을 테스트합니다. 추후에 구현되어야 할 부분입니다.")
    public void getPriceNegativeResult() {
        orderItem = new OrderItem(UUID.randomUUID(), 10, 1);
        UUID voucherId = UUID.randomUUID();
        Voucher createdVoucher = new FixedAmountVoucher(voucherId, new Discount(20), VoucherPolicy.FixedAmountVoucher);

        assertEquals(-10, createdVoucher.getRealPrice(orderItem));
    }
}