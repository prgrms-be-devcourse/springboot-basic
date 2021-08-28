package org.prgrms.kdt.devcourse.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("퍼센티지만큼 할인 - 깔끔히 나눠질때")
    void testDiscount() {
        PercentDiscountVoucher testVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);
        var discountResult= testVoucher.discount(1000);
        assertThat(discountResult,is(900L));
    }

    @Test
    @DisplayName("퍼센티지만큼 할인 - 내림이 필요한 경우")
    void testDiscountCeil() {
        PercentDiscountVoucher testVoucher = new PercentDiscountVoucher(UUID.randomUUID(),11);
        var discountResult= testVoucher.discount(1250);
        assertThat(discountResult,is(1112L));
    }

    @Test
    @DisplayName("1원 미만 금액은 0원으로 처리한다.")
    void testDiscountResultIsNotMinus() {
        PercentDiscountVoucher testVoucher = new PercentDiscountVoucher(UUID.randomUUID(),99);
        var discountResult= testVoucher.discount(10);
        assertThat(discountResult,is(0L));
    }

    @Test
    @DisplayName("바우처는 유효한 금액으로만 생성할 수 있다(0 보다크고 100이하이다)")
    void testVoucherAmount() {
        assertAll("FixedAmountVoucher creation",
                ()->assertThrows(IllegalArgumentException.class, ()->new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                ()->assertThrows(IllegalArgumentException.class, ()->new PercentDiscountVoucher(UUID.randomUUID(), -100)),
                ()->assertThrows(IllegalArgumentException.class, ()->new PercentDiscountVoucher(UUID.randomUUID(), 1000))
        );
    }
}