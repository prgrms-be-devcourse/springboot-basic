package org.prgrms.kdt.voucher.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("바우처 생성시 적절하지 않은 금액의 예외처리가 이루어져야 한다.")
    void testVoucherCreation(){
        assertAll(
                "FixedAmountVoucher 생성",
                // 0일경우
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                // 음수일경우
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -1))
        );
    }

    @Test
    @DisplayName("주어진 금액만큼 할인되어야 한다.")
    void testDiscount() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(), 100);
        assertThat(sut.discount(1000), is(900L));
    }

    @Test
    @DisplayName("할인 금액이 원금 초과시 원금은 0이 되어야한다.")
    void testMinusDiscountedAmount() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertThat(sut.discount(900), is(0L));
    }

}