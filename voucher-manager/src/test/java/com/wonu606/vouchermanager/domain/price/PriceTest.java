package com.wonu606.vouchermanager.domain.price;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Price 테스트")
public class PriceTest {

    @DisplayName("생성할 때 양수이면 생성된다.")
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 100.0, 200.0, 300.0, 1000000.0})
    public void Constructor_PositiveValue_CreatesPriceObject(double value) {
        // given & when
        Price price = new Price(value);

        // then
        assertThat(price.getValue()).isEqualTo(value);
    }

    @DisplayName("생성할 때 음수이면 생성된다.")
    @ParameterizedTest
    @ValueSource(doubles = {-100.0, -200.0, -300.0})
    public void Constructor_NegativeValue_ThrowsIllegalArgumentException() {
        // given & when & then
        Assertions.assertThatThrownBy(() -> new Price(-100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 양수여야 합니다.");
    }
}
