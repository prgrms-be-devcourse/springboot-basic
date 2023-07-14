package com.devcourse.springbootbasic.application.voucher.model;

import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchException;


class DiscountValueTest {

    @ParameterizedTest
    @DisplayName("할인값이 음수면 실패한다.")
    @MethodSource("provideNegativeDiscountValues")
    void DiscountValue_ParamNegative_Exception(VoucherType voucherType, String input) {
        Exception exception = catchException(() -> new DiscountValue(voucherType, input));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("할인율이 100% 넘으면 실패한다.")
    @MethodSource("provideUpper100DiscountValues")
    void DiscountValue_ParamUpper100_Exception(VoucherType voucherType, String input) {
        Exception exception = catchException(() -> new DiscountValue(voucherType, input));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    public static Stream<Arguments> provideNegativeDiscountValues() {
        return Stream.of(
                Arguments.arguments(VoucherType.PERCENT_DISCOUNT, "-2.9"),
                Arguments.arguments(VoucherType.FIXED_AMOUNT, "-0.1"),
                Arguments.arguments(VoucherType.PERCENT_DISCOUNT, "-10000")
        );
    }

    public static Stream<Arguments> provideUpper100DiscountValues() {
        return Stream.of(
                Arguments.arguments(VoucherType.PERCENT_DISCOUNT, "100.1"),
                Arguments.arguments(VoucherType.PERCENT_DISCOUNT, "101"),
                Arguments.arguments(VoucherType.PERCENT_DISCOUNT, "230230")
        );
    }

}