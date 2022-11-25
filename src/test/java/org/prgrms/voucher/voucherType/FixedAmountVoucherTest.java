package org.prgrms.voucher.voucherType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.exception.PaymentCannotBeNegativeException;
import org.prgrms.voucher.discountType.DiscountAmount;

class FixedAmountVoucherTest {

    @DisplayName("주어진 금액만큼 할인한 가격을 리턴한다")
    @Test
    void testDiscount() {
        //given
        long givenAmount = 100L;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),
            new DiscountAmount(givenAmount), LocalDateTime.now());
        //when
        long beforeDiscount = 1000;
        long discounted = fixedAmountVoucher.discount(beforeDiscount);

        //then
        assertEquals(900, discounted);
    }

    @DisplayName("할인이 결제금액보다 클 때  PaymentCannotBeNegativeException을 던진다")
    @Test
    void testWithMinus() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),
            new DiscountAmount(1000L), LocalDateTime.now());
        //when&then
        assertThrows(PaymentCannotBeNegativeException.class,
            () -> fixedAmountVoucher.discount(500L));
    }


}