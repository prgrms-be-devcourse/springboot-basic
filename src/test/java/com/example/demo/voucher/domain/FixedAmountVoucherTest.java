package com.example.demo.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("FixedAmountVoucher의 getter 테스트")
    void Fixed_Amount_Voucher_생성테스트() {
        // Given
        UUID id = UUID.randomUUID();
        long amount = 100;

        // When
        FixedAmountVoucher voucher = new FixedAmountVoucher(id, amount);

        // Then
        assertEquals(id, voucher.getVoucherId());
        assertEquals(amount, voucher.getValue());
        assertEquals("FixedAmountVoucher", voucher.getName());
    }

    @Test
    @DisplayName("할인 적용 테스트")
    void discount() {
        // Given
        UUID id = UUID.randomUUID();
        long amount = 100;
        long beforeDiscount = 500;
        long expectedAfterDiscount = beforeDiscount - amount;

        // When
        FixedAmountVoucher voucher = new FixedAmountVoucher(id, amount);
        long afterDiscount = voucher.discount(beforeDiscount);

        // Then
        assertEquals(expectedAfterDiscount, afterDiscount);
    }

}
