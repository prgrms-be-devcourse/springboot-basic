package org.prgrms.kdt.devcourse.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인")
    void testDiscount() {
        FixedAmountVoucher testVoucher = new FixedAmountVoucher(UUID.randomUUID(),100);
        var discountResult= testVoucher.discount(1000);
        assertThat(discountResult,is(900L));
    }

    @Test
    @DisplayName("할인 후 남은 금액이 음수면 0원이어야 한다.")
    void testDiscountResultIsNotMinus() {
        FixedAmountVoucher testVoucher = new FixedAmountVoucher(UUID.randomUUID(),1000000);
        var discountResult= testVoucher.discount(100);
        assertThat(discountResult,is(0L));
    }

    @Test
    @DisplayName("바우처는 유효한 금액으로만 생성할 수 있다(0 보다 커야한다.)")
    void testVoucherAmount() {
        assertAll("FixedAmountVoucher creation",
                ()->assertThrows(IllegalArgumentException.class, ()->new FixedAmountVoucher(UUID.randomUUID(), 0)),
                ()->assertThrows(IllegalArgumentException.class, ()->new FixedAmountVoucher(UUID.randomUUID(), -1000))
        );
    }
}