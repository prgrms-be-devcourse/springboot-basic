package com.example.springbootbasic.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @ParameterizedTest(name = "[{index}] discountValue = {0}")
    @MethodSource("whenConstructPercentDiscountThenSuccessDummy")
    @DisplayName("할인율 바우처 생성시 성공")
    void whenConstructPercentDiscountThenSuccessTest(Long discountValue) {
        PercentDiscountVoucher createdVoucher = new PercentDiscountVoucher(1L, discountValue);
        assertThat(createdVoucher.getDiscountValue(), is(discountValue));
    }

    @ParameterizedTest(name = "[{index}] discountValue = {0}")
    @MethodSource("whenConstructWrongRangePercentThenExceptionDummy")
    @DisplayName("할인율 바우처 생성시 입력한 할인율 범위에 맞지 않아 예외 처리")
    void whenConstructWrongRangePercentThenExceptionTest(Long discountValue) {
        assertThrowsExactly(IllegalArgumentException.class, () -> new PercentDiscountVoucher(1L, discountValue));
    }

    static Stream<Arguments> whenConstructPercentDiscountThenSuccessDummy() {
        return Stream.of(
                Arguments.arguments(0L),
                Arguments.arguments(1L),
                Arguments.arguments(2L),
                Arguments.arguments(10L),
                Arguments.arguments(20L),
                Arguments.arguments(99L),
                Arguments.arguments(100L)
        );
    }

    static Stream<Arguments> whenConstructWrongRangePercentThenExceptionDummy() {
        return Stream.of(
                Arguments.arguments(-1000L),
                Arguments.arguments(-100L),
                Arguments.arguments(-10L),
                Arguments.arguments(-1L),
                Arguments.arguments(101L),
                Arguments.arguments(102L),
                Arguments.arguments(1000L),
                Arguments.arguments(10000L)
        );
    }

}