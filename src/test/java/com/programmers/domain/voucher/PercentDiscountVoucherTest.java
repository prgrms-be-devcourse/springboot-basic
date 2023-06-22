package com.programmers.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {


    @DisplayName("퍼센트 바우처 할인 테스트")
    @ParameterizedTest
    @CsvSource(value = {"100, 50", "100, 0", "100, 100", "50, -1"})
    void discountPercentTest(long beforeDiscount, long amount) {

        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), amount);
        assertEquals(0, percentDiscountVoucher.discount(beforeDiscount));
    }
}