package org.prgms.voucherProgram.domain.voucher.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @DisplayName("할인금액이 1미만일때 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, -100})
    void should_ThrowsException_AmountIsLess1(long discountAmount) {
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 할인금액이 아닙니다.");
    }

    @DisplayName("할인금액이 1_000_000 초과면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {1_000_001, 1_000_200, 999_999_999, 123_456_778})
    void should_ThrowsException_AmountIsOver1_000_000(long discountPercent) {
        assertThatThrownBy(() -> new DiscountAmount(discountPercent))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 할인금액이 아닙니다.");
    }

    @DisplayName("정해진 할인금액으로 할인한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,100,900", "500,300,200", "10000,5000,5000"})
    void should_ReturnAfterDiscount(long beforeDiscount, long discountValue, long result) {
        DiscountAmount discountAmount = new DiscountAmount(discountValue);
        long discountPrice = discountAmount.discount(beforeDiscount);
        assertThat(discountPrice).isEqualTo(result);
    }
}
