package com.programmers.voucher.domain.voucher.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class VoucherTest {
    @ParameterizedTest
    @DisplayName("할인 값이 양수가 아닌 경우 예외가 발생한다.")
    @MethodSource("invalidDiscountAmountArguments")
    void 바우처_생성_예외(VoucherType voucherType, int amount) {
        assertThatThrownBy(() -> Voucher.create(voucherType, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 값은 양수만 가능합니다.");
    }

    @Test
    @DisplayName("할인율이 100 초과인 경우 예외가 발생한다.")
    void 퍼센트_할인_바우처_생성_예외() {
        assertThatThrownBy(() -> Voucher.create(VoucherType.PERCENT, 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 100을 넘을 수 없습니다.");
    }

    private static Stream<Arguments> invalidDiscountAmountArguments() {
        return Stream.of(
                arguments(VoucherType.FIXED, -1000),
                arguments(VoucherType.FIXED, 0),
                arguments(VoucherType.PERCENT, -30),
                arguments(VoucherType.FIXED, 0)
        );
    }
}
