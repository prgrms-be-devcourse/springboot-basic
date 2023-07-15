package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FixedAmountPolicyTest {

    @DisplayName("FixedAmountPolicy 가 할인 성공적으로 하는지 확인")
    @ParameterizedTest
    @CsvSource({
            "100, 10, 90", "300, 120, 180", "100, 200, 0"
    })
    void fixed_policy_success_discount(long beforeDiscount, long amount, long expected) {
        // given
        Voucher voucher = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), amount);

        // when
        long result = voucher.discount(beforeDiscount);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("FixedAmountVoucher 잘못된 할인이 예외를 던지는지 확인")
    @ParameterizedTest
    @CsvSource({
            "-500", "-10"
    })
    void fixed_policy_throw_wrong(long amount) {
        // then
        assertThatThrownBy(() -> new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), amount))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.INVALID_DISCOUNT_AMOUNT.getMessage());
    }
}
