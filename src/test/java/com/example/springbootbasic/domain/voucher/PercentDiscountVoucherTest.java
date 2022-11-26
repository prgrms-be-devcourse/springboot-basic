package com.example.springbootbasic.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class PercentDiscountVoucherTest {

    private final LocalDateTime startAt = LocalDateTime.of(2022, Month.OCTOBER, 25, 0, 0);
    private final LocalDateTime endAt = LocalDateTime.of(2022, Month.DECEMBER, 25, 0, 0);

    @ParameterizedTest(name = "[{index}] discountValue = {0}")
    @ValueSource(longs = {1L, 2L, 10L, 20L, 99L, 100L})
    @DisplayName("할인율 바우처 생성시 성공")
    void whenConstructPercentDiscountThenSuccessTest(Long discountValue) {
        PercentDiscountVoucher createdVoucher = new PercentDiscountVoucher(1L, discountValue, LocalDateTime.now(), startAt, endAt);
        assertThat(createdVoucher.getDiscountValue(), is(discountValue));
    }

    @ParameterizedTest(name = "[{index}] discountValue = {0}")
    @ValueSource(longs = {-1000L -100L, -10L, -1L, 101L, 102L, 1000L, 10000L})
    @DisplayName("할인율 바우처 생성시 입력한 할인율 범위에 맞지 않아 예외 처리")
    void whenConstructWrongRangePercentThenExceptionTest(Long discountValue) {
        assertThrowsExactly(IllegalArgumentException.class, () -> new PercentDiscountVoucher(1L, discountValue, LocalDateTime.now(), startAt, endAt));
    }
}