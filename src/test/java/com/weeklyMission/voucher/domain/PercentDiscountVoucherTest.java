package com.weeklyMission.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("기본 할인 기능 테스트")
    void testDiscount() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID().toString(), 90);
        assertThat(percentDiscountVoucher.discount(1000)).isEqualTo(100L);
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
    void testWithMinus() {
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID().toString(), -100))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("음수는 될 수 없음");
    }

    @Test
    @DisplayName("할인 금액은 0이 될 수 없다.")
    void testWithZero() {
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID().toString(), 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("0이 될 수 없음");
    }

    @Test
    @DisplayName("할인 금액은 최대 금액을 넘을 수 없다.")
    void testWithMaximum() {
        long MAX_FIXED_VOUCHER_AMOUNT = 99l;
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID().toString(), 100))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("%d보다 작아야 함".formatted(MAX_FIXED_VOUCHER_AMOUNT));
    }
}