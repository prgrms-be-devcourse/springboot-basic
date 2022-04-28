package com.blessing333.springbasic.domain.voucher.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherTest {
    @DisplayName("고정 금액 할인 바우처는 discountAmount 만큼 할인한다")
    @Test
    void fixedVoucherDiscountTest() {
        long beforePrice = 50000;
        long discountAmount = 5000;
        long expectedPrice = 45000;

        Voucher voucher = new Voucher(UUID.randomUUID(), Voucher.VoucherType.FIXED, 5000);

        Assertions.assertThat(voucher.discount(beforePrice)).isEqualTo(expectedPrice);
    }

    @DisplayName("비율 할인 바우처는 discountAmount % 만큼 할인한다")
    @Test
    void PercentVoucherDiscountTest() {
        long beforePrice = 50000;
        long discountRate = 50;
        long expectedPrice = 25000;

        Voucher voucher = new Voucher(UUID.randomUUID(), Voucher.VoucherType.PERCENT, discountRate);

        Assertions.assertThat(voucher.discount(beforePrice)).isEqualTo(expectedPrice);
    }

    @DisplayName("고정 할인 바우처에서 할인 금액이 원금보다 크다면 0원을 반환한다")
    @Test
    void discountOverPrice() {
        long beforePrice = 5000;
        long discountAmount = 50000;

        Voucher voucher = new Voucher(UUID.randomUUID(), Voucher.VoucherType.FIXED, discountAmount);

        Assertions.assertThat(voucher.discount(beforePrice)).isZero();
    }

    @DisplayName("고정 할인 바우처에서 할인 금액이 음수면 VoucherCreateFailException을 발생시킨다")
    @Test
    void discountAmountNegative() {
        long discountAmount = -5000;

        assertThrows(VoucherCreateFailException.class, () ->
                new Voucher(UUID.randomUUID(), Voucher.VoucherType.FIXED, discountAmount));
    }

    @DisplayName("비율 할인 바우처에서 할인 비율 범위는 1~100 사이 이여야하며, 이를 어길시 VoucherCreateFailException 발생시킨다")
    @Test
    void discountRateRangeTest() {
        assertThrows(VoucherCreateFailException.class, () ->
                new Voucher(UUID.randomUUID(), Voucher.VoucherType.PERCENT, 101));

    }
}
