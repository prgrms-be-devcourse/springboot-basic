package com.programmers.vouchermanagement.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("amount가 0 미만이면 예외가 발생한다.")
    void create_amountLessThan0_fail() {
        assertThatThrownBy(() -> new PercentDiscountVoucher(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Percent discount amount should be between 0 and 100");
    }

    @Test
    @DisplayName("amount가 100 초과이면 예외가 발생한다.")
    void create_amountMoreThan100_fail() {
        assertThatThrownBy(() -> new PercentDiscountVoucher(101L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Percent discount amount should be between 0 and 100");
    }
}
