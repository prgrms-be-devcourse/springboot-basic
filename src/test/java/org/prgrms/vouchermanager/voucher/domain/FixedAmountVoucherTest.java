package org.prgrms.vouchermanager.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("고정 금액을 디스카운트한다.")
    void testWIthDiscountAmount() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(10L);
        long beforeDiscountAmount = 100;

        //when
        long discounted = fixedAmountVoucher.discount(beforeDiscountAmount);

        //then
        assertEquals(90, discounted);
    }

    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountedAmount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1000L);
        long discount = fixedAmountVoucher.discount(100);

        assertEquals(0, discount);
    }

    @Test
    @DisplayName("할인액은 0이 될 수 없다.")
    void testVoucherCreationWithZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(0));
    }

    @Test
    @DisplayName("할인액은 minus가 될 수 없다.")
    void testVoucherCreationWithMinusAmount() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(-10));
    }

    @Test
    @DisplayName("할인액은 MAX_VOUCHER_AMOUNT을 초과할 수 없다.")
    void testVoucherCreationWithOverMaxAmount() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(FixedAmountVoucher.MAX_AMOUNT+1));
    }
}