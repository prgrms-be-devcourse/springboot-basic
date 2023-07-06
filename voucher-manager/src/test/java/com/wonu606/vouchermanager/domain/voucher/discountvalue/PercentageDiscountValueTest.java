package com.wonu606.vouchermanager.domain.voucher.discountvalue;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.voucher.discountvalue.PercentageDiscountValue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("PercentageDiscountValue 테스트")
class PercentageDiscountValueTest {

    @DisplayName("생성할 때_0~100 사이의 값이면_생성된다.")
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 50.0, 100.0})
    public void Constructor_ValidValue_CreatesPercentageDiscountValue(double value) {
        // Given & When
        PercentageDiscountValue percentageDiscountValue = new PercentageDiscountValue(value);

        // Then
        assertThat(percentageDiscountValue.getValue()).isEqualTo(value);
    }

    @DisplayName("생성할 때_0 미만이거나 100 초과이면_예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(doubles = {-1.0, 101.0})
    public void Constructor_InvalidValue_ThrowsIllegalArgumentException(double value) {
        // Given & When & Then
        Assertions.assertThatThrownBy(() -> new PercentageDiscountValue(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 0에서 100 사이여야 합니다.");
    }
}
