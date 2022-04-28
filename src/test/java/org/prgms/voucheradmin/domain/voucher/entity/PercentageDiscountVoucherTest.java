package org.prgms.voucheradmin.domain.voucher.entity;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PercentageDiscountVoucherTest {

    @ParameterizedTest(name = "{0}에서 {1}% 할인 = {2}")
    @MethodSource("percentageDiscountParameter")
    @DisplayName("퍼센트 할인 테스트")
    void discount(long beforeDiscount, int percent, long afterDiscount) {
        Voucher voucher = new PercentageDiscountVoucher(UUID.randomUUID(), percent, LocalDateTime.now());
        assertThat(voucher.discount(beforeDiscount), is(afterDiscount));
    }

    private static Stream<Arguments> percentageDiscountParameter() {
        return Stream.of(
                Arguments.of(10000L, 10, 9000L),
                Arguments.of(10000L, 100, 0L),
                Arguments.of(10000L, 88, 1200L),
                Arguments.of(10000L, 99, 100L),
                Arguments.of(9999L, 45, 5499L),
                Arguments.of(10L, 10, 9L)
        );
    }
}