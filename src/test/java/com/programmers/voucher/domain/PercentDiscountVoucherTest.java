package com.programmers.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountVoucherTest {
    @DisplayName("퍼센트 할인 바우처 discount() 메서드 성공 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "10, 1000, 900",
            "20, 1000, 800",
            "50, 1000, 500",
            "100, 1000, 0"})
    void discount(long voucherAmount, long originalPrice, long finalPrice) {
        Voucher voucher = PercentDiscountVoucher.of(UUID.randomUUID(), voucherAmount);
        assertThat(voucher.discount(originalPrice)).isEqualTo(finalPrice);
    }

    @DisplayName("생성할 퍼센 할인 바우처의 할인양이 범위안에 속하지 않을 경우 예외 발생 테스트")
    @ParameterizedTest
    @ValueSource(longs = {-1000, -1, 101, 102, 1003})
    void notIncludeDiscountAmountRange(long voucherAmount) {
        Assertions.assertThatThrownBy(() -> PercentDiscountVoucher.of(UUID.randomUUID(), voucherAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1 ~ 100 범위의 바우처 할인양을 입력해주세요");
    }

}
