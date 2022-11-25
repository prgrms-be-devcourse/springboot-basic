package org.prgrms.voucher.voucherType;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.discountType.DiscountRate;

class PercentDiscountVoucherTest {

    @DisplayName("주어진 금액만큼 할인한 가격을 리턴한다")
    @Test
    void testDiscount() {
        //given
        long givenDiscountRate = 30L;
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(
            UUID.randomUUID(), new DiscountRate(givenDiscountRate), LocalDateTime.now());
        //when
        long beforeDiscount = 1000;
        long discounted = percentDiscountVoucher.discount(beforeDiscount);

        //then
        assertEquals(700, discounted);
    }

}