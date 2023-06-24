package org.weekly.weekly.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DiscountMapTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "123"
            , "qwe"
            , "1q"
    })
    void 사용자_입력이_할인_맵에_없으면_예외발생(String userInput) {
        // when + then
        assertThatThrownBy(()->DiscountMap.getDiscountMap(userInput))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    void 할인번호를_입력하면_통과() {
        // Given
        String fixedUserInput = "1";
        String percentUserInput = "2";

        // when
        DiscountMap fixedDiscount = DiscountMap.getDiscountMap(fixedUserInput);
        DiscountMap percentDiscount = DiscountMap.getDiscountMap(percentUserInput);

        // then
        assertThat(fixedDiscount).isEqualTo(DiscountMap.FIXED);
        assertThat(percentDiscount).isEqualTo(DiscountMap.PERCENT);
    }
}
