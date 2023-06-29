package com.prgrms.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    @Test
    public void testDiscount_ValidDiscount() {
        long validDiscount = 100;
        Discount discount = new Discount(validDiscount);

        assertEquals(validDiscount, discount.getDiscount());
    }

    @Test
    @DisplayName("음수 할인 인자에 대해서 예외를 던지는지 테스트")
    public void testDiscount_InvalidDiscount_ThrowsException() {
        long invalidDiscount = -50;

        assertThrows(IllegalArgumentException.class, () -> new Discount(invalidDiscount));
    }
}