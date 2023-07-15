package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PercentDiscountPolicyTest {

    @DisplayName("PercentDiscountPolicy 가 할인 성공적으로 하는지 확인")
    @ParameterizedTest
    @CsvSource({
            "100, 10, 90", "15000, 50, 7500"
    })
    void percent_policy_success_discount(long beforeDiscount, long amount, long expected) {

        // given
        Voucher voucher = new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), amount);

        // when
        long result = voucher.discount(beforeDiscount);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("PercentDiscountVoucher 잘못된 할인이 예외를 던지는지 확인")
    @ParameterizedTest
    @CsvSource({
            "0", "-10", "150"
    })
    void percent_policy_throw_wrong(long amount) {
        // then
        assertThatThrownBy(() -> new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), amount))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(ErrorMessage.INVALID_DISCOUNT_AMOUNT.getMessage());
    }
}

