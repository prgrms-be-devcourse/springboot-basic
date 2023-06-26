package com.programmers.voucher.view.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DiscountAmountTest {
    @ParameterizedTest
    @DisplayName("바우처 종류와 상관없이, 값이 양수가 아닌 경우 예외가 발생한다.")
    @MethodSource("invalidDiscountAmountArguments")
    void 할인_값_양수_X(VoucherType voucherType, long amount) {
        assertThatThrownBy(() -> new DiscountAmount(voucherType, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 값은 양수만 가능합니다.");
    }

    @ParameterizedTest
    @DisplayName("할인율이 100 초과인 경우 예외가 발생한다.")
    @ValueSource(longs = {105L, 2000L})
    void 할인율_100_초과(long percent) {
        assertThatThrownBy(() -> new DiscountAmount(VoucherType.PERCENT_DISCOUNT, percent))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 100을 넘을 수 없습니다.");
    }

    private static Stream<Arguments> invalidDiscountAmountArguments() {
        return Stream.of(
                arguments(VoucherType.FIXED_AMOUNT, -1000L),
                arguments(VoucherType.FIXED_AMOUNT, 0),
                arguments(VoucherType.PERCENT_DISCOUNT, -30L),
                arguments(VoucherType.FIXED_AMOUNT, 0)
        );
    }
}
