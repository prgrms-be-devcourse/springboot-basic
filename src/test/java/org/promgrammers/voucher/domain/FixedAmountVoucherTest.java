package org.promgrammers.voucher.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class FixedAmountVoucherTest {
    @Test
    @DisplayName("정액할인 : 100 -90 = 10")
    void testCalculateDiscount() {

        //given
        long amount = 10;
        long price = 100;
        UUID id = UUID.randomUUID();

        FixedAmountVoucher voucher = new FixedAmountVoucher(amount, id);

        //when
        long discountedPrice = voucher.calculateDiscount(price);

        //then
        long expectedDiscountedPrice = 90;
        Assertions.assertEquals(expectedDiscountedPrice, discountedPrice);
    }

    @Test
    @DisplayName("정액할인 : 할인된 가격이 0 보다 작은 경우 ")
    void testMinCalculateDiscount() {
        //given
        long amount = 100;
        long price = 50;
        UUID id = UUID.randomUUID();

        FixedAmountVoucher voucher = new FixedAmountVoucher(amount, id);

        //when
        long discountedPrice = voucher.calculateDiscount(price);

        //then
        long expectedDiscountedPrice = 0; // 최소값이 음수일 경우 0

        Assertions.assertEquals(expectedDiscountedPrice, discountedPrice);
    }

    @Test
    @DisplayName("정액할인 : 할인 금액이 가격보다 큰 경우")
    void testMaxCalculateDiscount() {
        //given
        long amount = 200;
        long price = 100;
        UUID id = UUID.randomUUID();

        FixedAmountVoucher voucher = new FixedAmountVoucher(amount, id);

        //when
        long discountedPrice = voucher.calculateDiscount(price);

        //then
        long expectedDiscountedPrice = 0; // 할인된 가격이 0 미만인 경우 0

        Assertions.assertEquals(expectedDiscountedPrice, discountedPrice);
    }
}
