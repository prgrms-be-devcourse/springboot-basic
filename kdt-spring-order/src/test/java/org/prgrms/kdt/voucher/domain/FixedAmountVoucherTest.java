package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        //given
        long discountAmount = 100;
        long moneyBeforeDiscount = 900;
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);

        //when
        long moneyAfterDiscount = voucher.discount(moneyBeforeDiscount);

        //then
        assertThat(moneyAfterDiscount, is(800L));
    }

    @Test
    @DisplayName("유효하지 않은 할인 금액을 이용하여 Voucher 생성하면 예외가 발생한다.")
    void testVoucherCreation(){
        //given //when //then
        assertAll("Creating FixedAmountVoucher using invalid discount money",
                ()-> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                ()-> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                ()-> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 1000000000)));
    }
}