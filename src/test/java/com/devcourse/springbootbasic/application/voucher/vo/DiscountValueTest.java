package com.devcourse.springbootbasic.application.voucher.vo;

import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.vo.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.vo.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscountValueTest {

    @ParameterizedTest
    @DisplayName("할인값이 음수면 실패한다.")
    @MethodSource("provideNegetiveDiscountValues")
    void DiscountValue_ParamNegetive_Exception(VoucherType voucherType, String input) {
        assertThrows(InvalidDataException.class, () -> new DiscountValue(voucherType, input));
    }

    @ParameterizedTest
    @DisplayName("할인율이 100% 넘으면 실패한다.")
    @MethodSource("provideUpper100DiscountValues")
    void DiscountValue_ParamUpper100_Exception(VoucherType voucherType, String input) {
        assertThrows(InvalidDataException.class, () -> new DiscountValue(voucherType, input));
    }

    public static Stream<Arguments> provideNegetiveDiscountValues() {
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