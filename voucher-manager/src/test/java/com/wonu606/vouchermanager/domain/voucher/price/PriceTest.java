package com.wonu606.vouchermanager.domain.voucher.price;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Price 테스트")
public class PriceTest {

    @DisplayName("생성할 때_양수이면_생성된다.")
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 100.0, 200.0, 300.0, 1000000.0})
    public void Constructor_PositiveValue_CreatesPriceObject(double value) {
        // Given & When
        Price price = new Price(value);

        // Then
        assertThat(price.getValue()).isEqualTo(value);
    }

    @DisplayName("생성할 때_음수이면_예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(doubles = {-100.0, -200.0, -300.0})
    public void Constructor_NegativeValue_ThrowsIllegalArgumentException() {
        // Given & When & Then
        Assertions.assertThatThrownBy(() -> new Price(-100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 양수여야 합니다.");
    }
}
