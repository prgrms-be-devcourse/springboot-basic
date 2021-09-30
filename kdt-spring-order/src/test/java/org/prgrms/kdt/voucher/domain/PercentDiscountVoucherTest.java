package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.exception.VoucherPercentRangeException;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("할인률을 적용한 금액을 반환한다.")
    void testDiscount(){
        //given
        long discountPercent = 10;
        long moneyBeforeDiscount = 100;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), discountPercent);

        //when
        long moneyAfterDiscount = voucher.discount(moneyBeforeDiscount);

        //then
        assertThat(moneyAfterDiscount, is(10L));
    }

    @Test
    @DisplayName("유효하지 않은 할인률을 이용하여 Voucher 생성하면 예외가 발생한다.")
    void testCreateVoucherUsingInvalidPercent(){
        //given //when //then
        assertThrows(VoucherPercentRangeException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -10));
        assertThrows(VoucherPercentRangeException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 105));
    }

}