package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountTest {
    
    @DisplayName("고정 할인값 discount() 메서드 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "100, 99, 1",
            "50, 2, 48",
            "445153, 100000, 345153",
            "1000000, 999999, 1",
            "30, 35, 0",
            "20, 20, 0",
            "5, 3, 2"
    })
    void fixedDiscountMethodTest(long itemPrice, long amount, long expected) {
        FixedDiscount fixedDiscount = new FixedDiscount(amount);

        if (expected < 0 ) expected = 0;

        assertEquals(expected, fixedDiscount.applyDiscount(itemPrice));
    }
    
    @DisplayName("퍼센트 할인 discount() 메서드 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "100, 50, 50",
            "50, 2, 49",
            "30, 10, 27",
            "30, 20, 24",
            "20, 100, 0",
            "1000, 10, 900"
    })
    void percentDiscountMethodTest(long itemPrice, long percent, long expected) {
        PercentDiscount percentDiscount = new PercentDiscount(percent);

        assertEquals(expected, percentDiscount.applyDiscount(itemPrice));
    }
}
