package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {


    @DisplayName("퍼센트 바우처 유효기간 테스트")
    @Test
    void PercentVoucherAvailableTest() {
        long amount = 10;
        LocalDate localDateTime = LocalDate.of(2023,6, 17);

        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), amount, localDateTime);
        assertTrue(percentDiscountVoucher.available());
    }

    @DisplayName("퍼센트 바우처 할인 테스트")
    @ParameterizedTest
    @CsvSource(value = {"100, 50", "100, 0", "100, 100", "50, -1"})
    void discountPercentTest(long beforeDiscount, long amount) {
        LocalDate localDate = LocalDate.of(2023, 6, 20);

        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), amount, localDate);
        assertEquals(50, percentDiscountVoucher.discount(beforeDiscount));
    }
}