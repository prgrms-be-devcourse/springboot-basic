package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {


    @DisplayName("유효하지 않은 퍼센트 바우처 할인 테스트")
    @ParameterizedTest
    @CsvSource(value = {"100, 90", "50, 2", "5, 3", "126346, 100", "1235123511, 14353", "100, 99"})
    void notAvailableVoucherDiscountTest(long beforeDiscount, long amount) {
        LocalDate localDate = LocalDate.of(2023, 6, 13);

        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), amount, localDate);
        assertEquals(beforeDiscount - amount, percentDiscountVoucher.discount(beforeDiscount));
    }

    @DisplayName("유효한 퍼센트 바우처 할인 테스트")
    @ParameterizedTest
    @CsvSource(value = {"100, 90", "50, 2", "5, 3", "126346, 100", "1235123511, 143532343", "100, 99"})
    void availableVoucherDiscountTest(long beforeDiscount, long amount) {

        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), amount, LocalDate.now());
        assertEquals(beforeDiscount - amount, percentDiscountVoucher.discount(beforeDiscount));
    }
}