package com.demo.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PercentDiscountVoucherTest {
    // given
    private static final Voucher percentVoucher = new PercentDiscountVoucher(20);

    @Test
    @DisplayName("Percent Voucher이 voucherId 값을 정상적으로 가져오는지 확인하는 테스트")
    void getVoucherId() {
        // given
        UUID uuid = UUID.randomUUID();
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(20);

        // when
        UUID voucherId = voucher.getVoucherId();

        // then
        assertEquals(voucherId, uuid);
    }


    @Test
    @DisplayName("Percent Voucher이 amount 값을 잘 가져오는지 확인하는 테스트")
    void getAmount() {
        assertEquals(20, percentVoucher.getAmount());
    }

    @Test
    @DisplayName("Percent Voucher이 바우처 타입 설명을 제대로 리턴하는지 확인하는 테스트")
    void getVoucherType() {
        assertEquals("비율 할인 바우처", percentVoucher.getVoucherType());
    }

    @Test
    @DisplayName("Percent Voucher이 할인 단위와 함께 할인 amount를 잘 리턴하는지 확인하는 테스트")
    void getDiscountInfo() {
        assertEquals("20%", percentVoucher.getDiscountInfo());

    }
}