package org.promgrammers.voucher.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {
    @Test
    @DisplayName("정률할인 :20% 할인 100 - (100 * 20 / 100 ) = 80")
    void testCalculateDiscount() {
        //given
        long amount = 20;
        long price = 100;
        UUID id = UUID.randomUUID();

        PercentDiscountVoucher voucher = new PercentDiscountVoucher(amount, id);

        //when
        long discountedPrice = voucher.calculateDiscount(price);

        //then
        long expectedDiscountedPrice = 80;

        Assertions.assertEquals(expectedDiscountedPrice, discountedPrice);
    }
}