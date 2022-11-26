package com.example.springbootbasic.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class FixedAmountVoucherTest {

    private final LocalDateTime startAt = LocalDateTime.of(2022, Month.OCTOBER, 25, 0, 0);
    private final LocalDateTime endAt = LocalDateTime.of(2022, Month.DECEMBER, 25, 0, 0);

    @ParameterizedTest(name = "[{index}] discountValue = {0}")
    @MethodSource("whenConstructFixedAmountThenSuccessDummy")
    @DisplayName("고정 할인 바우처 생성시 성공")
    void whenConstructFixedAmountThenSuccessTest(Long discountValue) {
        FixedAmountVoucher createdVoucher = new FixedAmountVoucher(discountValue, LocalDateTime.now(), startAt, endAt);
        assertThat(createdVoucher.getDiscountValue(), is(discountValue));
    }

    @ParameterizedTest(name = "[{index}] discountValue = {0}")
    @MethodSource("whenConstructWrongRangeFixedAmountThenExceptionDummy")
    @DisplayName("고정 할 인 바우처 생성시 입력한 할인 금액이 범위에 맞지 않아 예외 처리")
    void whenConstructWrongRangeFixedAmountThenExceptionTest(Long discountValue) {
        assertThrowsExactly(IllegalArgumentException.class, () -> new FixedAmountVoucher(discountValue, LocalDateTime.now(), startAt, endAt));
    }

    static Stream<Arguments> whenConstructFixedAmountThenSuccessDummy() {
        return Stream.of(
                Arguments.arguments(10L),
                Arguments.arguments(100L),
                Arguments.arguments(1000L),
                Arguments.arguments(10000L),
                Arguments.arguments(50000L)
        );
    }

    static Stream<Arguments> whenConstructWrongRangeFixedAmountThenExceptionDummy() {
        return Stream.of(
                Arguments.arguments(-50000L),
                Arguments.arguments(-10000L),
                Arguments.arguments(-1000L),
                Arguments.arguments(-100L),
                Arguments.arguments(-10L),
                Arguments.arguments(0L),
                Arguments.arguments(-1L),
                Arguments.arguments(50001L),
                Arguments.arguments(50001L),
                Arguments.arguments(100000L),
                Arguments.arguments(1000000L)
        );
    }
}