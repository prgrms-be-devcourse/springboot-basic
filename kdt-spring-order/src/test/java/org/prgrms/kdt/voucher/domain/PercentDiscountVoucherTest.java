package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("바우처 생성시 적절하지 않은 금액의 예외처리가 이루어져야 한다.")
    void testVoucherCreation(){
        assertAll(
                "PercentDiscountVoucher 생성",
                // 0일경우
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                // 음수일경우
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -1)),
                // 100을 초과할 경우
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 101))
        );
    }

    @Test
    @DisplayName("주어진 할인율만큼 할인되어야 한다.")
    void testDiscount() {
        var sut = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        assertThat(sut.discount(1000), is(900L));
    }

    @Test
    @DisplayName("할인율이 100%일 경우 원금은 0이 되어야 한다.")
    void testMinusDiscountedAmount() {
        var sut = new PercentDiscountVoucher(UUID.randomUUID(), 100);
        assertThat(sut.discount(100), is(0L));
    }


}