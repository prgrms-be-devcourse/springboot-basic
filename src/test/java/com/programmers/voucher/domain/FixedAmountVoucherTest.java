package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountVoucherTest {
    


    @DisplayName("유효하지 않은 바우처 할인 테스트")
    @ParameterizedTest
    @CsvSource(value = {"100, 90", "50, 2", "5, 3", "126346, 100", "1235123511, 14353", "100, 99"})
    void notAvailableVoucherDiscountTest(long beforeDiscount, long amount) {
        LocalDate localDate = LocalDate.of(2023,6, 13);

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount, localDate);
        assertEquals(beforeDiscount - amount, fixedAmountVoucher.discount(beforeDiscount));
    }

    @DisplayName("유요한 바우처 할인 테스트")
    @ParameterizedTest
    @CsvSource(value = {"100, 90", "50, 2", "5, 3", "126346, 100", "1235123511, 143532343", "100, 99"})
    void availableVoucherDiscountTest(long beforeDiscount, long amount) {
        LocalDate localDate = LocalDate.of(2023,6, 23);

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount, localDate);
        assertEquals(beforeDiscount - amount, fixedAmountVoucher.discount(beforeDiscount));
    }


}