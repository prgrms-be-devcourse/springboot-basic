package com.devcourse.voucher.domain;

import com.devcourse.voucher.domain.Voucher.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {
    private final LocalDateTime expiredAt = LocalDateTime.now();

    @Test
    @DisplayName("고정 타입으로 생성하면 고정 할인 정책을 가진 새 바우처가 생성되어야 한다.")
    void createFixedPolicyTest() {
        // given
        int discountAmount = 5000;

        // when
        Voucher voucher = new Voucher(discountAmount, expiredAt, Type.FIXED);

        // then
        assertThat(voucher.getPolicy()).isInstanceOf(FixedAmountPolicy.class);
        assertThat(voucher.isUsed()).isFalse();
    }

    @Test
    @DisplayName("퍼센트 타입으로 생성하면 퍼센트 할인 정책을 가진 새 바우처가 생성되어야 한다.")
    void createPercentPolicyTest() {
        // given
        int discountRate = 50;

        // when
        Voucher voucher = new Voucher(discountRate, expiredAt, Type.PERCENT);

        // then
        assertThat(voucher.getPolicy()).isInstanceOf(PercentDiscountPolicy.class);
        assertThat(voucher.isUsed()).isFalse();
    }
}
