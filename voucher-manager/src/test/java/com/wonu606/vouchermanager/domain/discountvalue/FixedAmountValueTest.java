package com.wonu606.vouchermanager.domain.discountvalue;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("FixedAmountValue 테스트")
class FixedAmountValueTest {

    @DisplayName("생성할 때 양수이면 생성된다.")
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 100.0, 200.0, 300.0, 1000000.0})
    public void Constructor_PositiveValue_CreatesFixedAmountValue(double value) {
        // given & when
        FixedAmountValue fixedAmountValue = new FixedAmountValue(value);

        // then
        assertThat(fixedAmountValue.getValue()).isEqualTo(value);
    }

    @DisplayName("생성할 때 음수이면 생성된다.")
    @ParameterizedTest
    @ValueSource(doubles = {-100.0, -200.0, -300.0})
    public void Constructor_NegativeValue_ThrowsIllegalArgumentException(double value) {
        // given & when & then
        Assertions.assertThatThrownBy(() -> new FixedAmountValue(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인할 금액은 양수여야 합니다.");
    }
}