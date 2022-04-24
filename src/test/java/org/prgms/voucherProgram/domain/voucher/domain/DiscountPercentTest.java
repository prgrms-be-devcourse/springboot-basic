package org.prgms.voucherProgram.domain.voucher.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountPercentTest {

    @DisplayName("할인퍼센트가 1미만이면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 140, 1000})
    void percentDiscount_PercentIsLessOne_ThrowsException(long discountPercent) {
        assertThatThrownBy(() -> new DiscountPercent(discountPercent))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 할인퍼센트가 아닙니다.");
    }

    @DisplayName("할인퍼센트가 100초과면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {101, 200, 140, 1000})
    void percentDiscount_PercentIsOverHundred_ThrowsException(long discountPercent) {
        assertThatThrownBy(() -> new DiscountPercent(discountPercent))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 할인퍼센트가 아닙니다.");
    }

    @DisplayName("정해진 할인퍼센트로 할인한다.")
    @ParameterizedTest
    @CsvSource(value = {"1000,10,900", "2000,20,1600", "532,30,372", "1000,100,0"})
    void discount_Percent_ReturnDiscountPrice(long beforeDiscount, long discountValue, long result) {
        DiscountPercent discountPercent = new DiscountPercent(discountValue);
        long discountPrice = discountPercent.discount(beforeDiscount);
        assertThat(discountPrice).isEqualTo(result);
    }

}
