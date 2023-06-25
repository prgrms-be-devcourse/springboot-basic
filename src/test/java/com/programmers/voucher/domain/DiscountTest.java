package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {
    
    @DisplayName("고정 할인값 discount() 메서드 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "100, 99",
            "50, 2",
            "445153, 100000",
            "1000000, 999999",
            "30, 30",
            "20, 25",
            "5, 3"
    })
    void discountAmountDiscountMethodTest(long itemPrice, long amount) {
        DiscountAmount discountAmount = new DiscountAmount(amount);
        assertEquals(itemPrice - amount, discountAmount.discount(itemPrice));
    }
    
    @DisplayName("퍼센트 할인 discount() 메서드 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "100, 50",
            "50, 2",
            "30, 10",
            "3, 10,",
            "20, 100",
            "1000, 10"
    })
    void discountPercentDiscountMethodTest(long itemPrice, long percent) {
        DiscountPercent discountPercent = new DiscountPercent(percent);
        assertEquals((long)(itemPrice * (1 - (percent / 100.0))), discountPercent.discount(itemPrice));
    }
}