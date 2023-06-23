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
    
    @DisplayName("바우처 유효기간 테스트")
    @Test
    void voucherAvailableTest() {
        UUID id = UUID.randomUUID();
        long amount = 10;
        LocalDate localDateTime = LocalDate.of(2023,6, 15);

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(id, amount, localDateTime);
        assertTrue(fixedAmountVoucher.available());
    }

    @DisplayName("바우처 할인 테스트")
    @ParameterizedTest
    @CsvSource(value = {"100, 120", "50, 2", "0, 0", "3, 100", "100, 100", "100, 99"})
    void discountTest(long beforeDiscount, long amount) {
        LocalDate localDate = LocalDate.of(2023,6, 15);

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount, LocalDate.now());
        FixedAmountVoucher fixedAmountVoucher2 = new FixedAmountVoucher(UUID.randomUUID(), amount, localDate);
        assertEquals(beforeDiscount - amount, fixedAmountVoucher2.discount(beforeDiscount));
    }

}