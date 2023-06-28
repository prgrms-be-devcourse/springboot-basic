package org.prgrms.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FixedAmountVoucherTest {

    @ParameterizedTest
    @DisplayName("고정 금액 할인 바우처 실패 테스트")
    @ValueSource(doubles = {10000})
    void fixedAmountVoucherFailTest(double discountAmount) {
        UUID id = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(id, discountAmount);
        double stockPrice = 100;
        double result = voucher.discount(stockPrice);

        assertThat(result).isEqualTo(0);
    }

    @ParameterizedTest
    @DisplayName("고정 금액 할인 음수 예외 테스트")
    @ValueSource(doubles = {-10000})
    void fixedAmountVoucherNegativeFailTest(double discountAmount) {
        UUID id = UUID.randomUUID();

        assertThatThrownBy(() -> new FixedAmountVoucher(id, discountAmount))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("금액은 양수여야 합니다.");
    }

    @ParameterizedTest
    @DisplayName("고정 금액 할인 바우처 성공 테스트")
    @ValueSource(doubles = {10000})
    void fixedAmountVoucherSuccessTest(double discountAmount) {
        UUID id = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(id, discountAmount);
        double stockPrice = 20000;
        double result = voucher.discount(stockPrice);

        assertThat(result).isEqualTo(10000);
    }
}