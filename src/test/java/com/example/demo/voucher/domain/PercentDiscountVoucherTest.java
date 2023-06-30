package com.example.demo.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("Percent 바우처 할인 적용 테스트")
    void discount() {
        // Given
        UUID voucherId = UUID.randomUUID();
        long percent = 50;

        // When
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, percent);

        long discounted = voucher.discount(10000);

        // Then
        assertEquals(5000, discounted);
    }

    @Test
    @DisplayName("Percent 바우처 할인 적용 예외 테스트: 100 이상")
    void invalidPercentGreater() {
        UUID voucherId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(voucherId, 101));
    }

    @Test
    @DisplayName("Percent 바우처 할인 적용 예외 테스트: 0 이하")
    void invalidPercentZero() {
        UUID voucherId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(voucherId, 0));
    }

    @Test
    @DisplayName("Percent 바우처 할인 적용 예외 테스트: 음수")
    void invalidPercentNegative() {
        UUID voucherId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(voucherId, -1));
    }

    @Test
    @DisplayName("Percent 바우처 getter 테스트")
    void getter() {
        // Given
        UUID voucherId = UUID.randomUUID();
        long percent = 50;

        // When
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, percent);

        // Then
        assertEquals(percent, voucher.getValue());
        assertEquals("PercentDiscountVoucher", voucher.getName());
        assertEquals(voucherId, voucher.getVoucherId());
    }

}
