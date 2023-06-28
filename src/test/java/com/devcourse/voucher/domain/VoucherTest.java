package com.devcourse.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {
    private final LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);

    @ParameterizedTest
    @DisplayName("고정 할인 바우처로 계산 시 결과가 같으며 사용완료 상태여야 한다.")
    @CsvSource({"1000, 500, 500", "2500, 1000, 1500", "127, 1, 126"})
    void applyFixedAmountTest(int price, int discountAmount, BigDecimal result) {
        // given
        Voucher voucher = Voucher.fixed(discountAmount, expiredAt);

        // when
        BigDecimal appliedPrice = voucher.apply(price);

        // then
        assertThat(appliedPrice).isEqualTo(result);
        assertThat(voucher.isUsed()).isTrue();
    }

    @ParameterizedTest
    @DisplayName("퍼센트 할인 바우처로 계산 시 결과가 같으며 사용완료 상태여야 한다.")
    @CsvSource({"1000, 50, 500.0", "2500, 100, 0.0", "120, 10, 108.0"})
    void applyPercentDiscountTest(int price, int discountRate, BigDecimal result) {
        // given
        Voucher voucher = Voucher.percent(discountRate, expiredAt);

        // when
        BigDecimal appliedPrice = voucher.apply(price);

        // then
        assertThat(appliedPrice).isEqualTo(result);
        assertThat(voucher.isUsed()).isTrue();
    }
}
