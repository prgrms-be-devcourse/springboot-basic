package com.example.demo.voucher.application;

import com.example.demo.voucher.domain.FixedAmountVoucher;
import com.example.demo.voucher.domain.PercentDiscountVoucher;
import com.example.demo.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {

    @Test
    @DisplayName("FIXED_AMOUNT_VOUCHER 생성 테스트")
    void createVoucher_fixed() {
        // Given
        UUID id = UUID.randomUUID();
        long value = 100;

        // When
        Voucher voucher = VoucherType.FIXED_AMOUNT_VOUCHER.createVoucher(id, value);

        // Then
        assertAll("voucher",
                () -> assertTrue(voucher instanceof FixedAmountVoucher, "Voucher should be instance of FixedAmountVoucher"),
                () -> assertEquals(id, voucher.getVoucherId(), "Voucher id should match"),
                () -> assertEquals(value, voucher.getValue(), "Voucher value should match")
        );
    }

    @Test
    @DisplayName("PERCENT_DISCOUNT_VOUCHER 생성 테스트")
    void createVoucher_percent() {
        // Given
        UUID id = UUID.randomUUID();
        long value = 20;

        // When
        Voucher voucher = VoucherType.PERCENT_DISCOUNT_VOUCHER.createVoucher(id, value);

        // Then
        assertTrue(voucher instanceof PercentDiscountVoucher);
        assertEquals(id, voucher.getVoucherId());
        assertEquals(value, voucher.getValue());
    }

    @Test
    @DisplayName("1라는 명령으로 부터 fixed 바우처가 생성되는지 확인하는 테스트")
    void fromCounterFixedAmount() {
        // Given
        String fixedAmountVoucherCounter = "1";

        // When
        Optional<VoucherType> fixedAmountVoucherType = VoucherType.fromCounter(fixedAmountVoucherCounter);

        // Then
        assertTrue(fixedAmountVoucherType.isPresent());
        assertEquals(VoucherType.FIXED_AMOUNT_VOUCHER, fixedAmountVoucherType.get());
    }

    @Test
    @DisplayName("2라는 명령으로 부터 percent 바우처가 생성되는지 확인하는 테스트")
    void fromCounterPercentDiscount() {
        // Given
        String percentDiscountVoucherCounter = "2";

        // When
        Optional<VoucherType> percentDiscountVoucherType = VoucherType.fromCounter(percentDiscountVoucherCounter);

        // Then
        assertTrue(percentDiscountVoucherType.isPresent());
        assertEquals(VoucherType.PERCENT_DISCOUNT_VOUCHER, percentDiscountVoucherType.get());
    }

}
