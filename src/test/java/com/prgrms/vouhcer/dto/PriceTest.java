package com.prgrms.vouhcer.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.common.exception.NegativeArgumentException;
import com.prgrms.order.model.Price;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PriceTest {

    @Test
    @DisplayName("가격이 음수인 경우 예외를 던진다.")
    void getValue_NegativePrice_ThrowsException() {
        //given
        double value = -100_000;

        //when_then
        assertThatThrownBy(() -> new Price(value))
                .isInstanceOf(NegativeArgumentException.class);
    }

    @DisplayName("제품의 가격과 할인 금액을 넣었을 때 할인된 가격을 반환한다.")
    @ParameterizedTest
    @MethodSource("saledPriceProvider")
    void getSaledPrice_DiscountAmount_SaledPrice(double cost, double discountAmount,
            double expectedSaledPrice) {
        // given
        Price price = new Price(cost);

        // when
        Price saledPrice = price.getSaledPrice(discountAmount);

        // then
        assertThat(saledPrice.cost()).isEqualTo(expectedSaledPrice);
    }

    private static Stream<Arguments> saledPriceProvider() {
        return Stream.of(
                Arguments.of(10.0, 3.0, 7.0),
                Arguments.of(20.0, 5.0, 15.0),
                Arguments.of(15.0, 2.0, 13.0)
        );
    }

}
