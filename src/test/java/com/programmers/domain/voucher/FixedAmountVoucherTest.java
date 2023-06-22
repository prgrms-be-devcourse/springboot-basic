package com.programmers.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountVoucherTest {
    
    @DisplayName("바우처 유효기간 테스트")
    @Test
    void voucherAvailableTest() {
        UUID id = UUID.randomUUID();
        long amount = 10;

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(id, amount);
        assertTrue(fixedAmountVoucher.available());
    }

    @DisplayName("바우처 할인 테스트")
    @ParameterizedTest
    @CsvSource(value = {"100, 120", "50, 2", "0, 0", "3, 100"})
    void test(long beforeDiscount, long amount) {
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        assertEquals(0, fixedAmountVoucher.discount(beforeDiscount));
    }

}