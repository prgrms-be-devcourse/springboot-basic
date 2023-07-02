package com.demo.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedAmountVoucherTest {
    // given
    private static final Voucher fixedAmountVoucher = new FixedAmountVoucher(2000);

    @Test
    @DisplayName("Fixed Voucher이 voucherId 값을 정상적으로 가져오는지 확인하는 테스트")
    void getVoucherId() {
        // given
        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(2000);

        // when
        UUID voucherId = voucher.getVoucherId();

        // then
        assertEquals(voucherId, uuid);
    }


    @Test
    @DisplayName("Fixed Voucher이 amount 값을 잘 가져오는지 확인하는 테스트")
    void getAmount() {
        assertEquals(2000, fixedAmountVoucher.getAmount());
    }

    @Test
    @DisplayName("Fixed Voucher이 바우처 타입 설명을 제대로 리턴하는지 확인하는 테스트")
    void getVoucherType() {
        assertEquals("고정 할인 바우처", fixedAmountVoucher.getVoucherType());
    }

    @Test
    @DisplayName("Fixed Voucher이 할인 단위와 함께 할인 amount를 잘 리턴하는지 확인하는 테스트")
    void getDiscountInfo() {
        assertEquals("2000원", fixedAmountVoucher.getDiscountInfo());

    }
}