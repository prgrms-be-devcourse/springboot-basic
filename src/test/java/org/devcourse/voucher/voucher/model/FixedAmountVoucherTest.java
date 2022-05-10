package org.devcourse.voucher.voucher.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FixedAmountVoucherTest {

    private final FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

    @Test
    @DisplayName("실제 디스카운트 금액만큼 값이 차감되는지 테스트")
    void discountTest() {
        assertThat(voucher.discount(3000)).isEqualTo(2000);
    }

    @Test
    @DisplayName("디스카운트 금액이 실제 상품 가격보다 클 경우 0을 반환")
    void discountOperationNegativeValueTest() {
        assertThat(voucher.discount(100)).isZero();
    }

    @Test
    @DisplayName("상품의 가격이 음수로 들어올 경우 예외 발생하는지 테스트")
    void priceNegativeValueTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> voucher.discount(-11));
    }
}