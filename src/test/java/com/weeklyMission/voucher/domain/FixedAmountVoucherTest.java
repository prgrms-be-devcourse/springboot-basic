package com.weeklyMission.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.text.MessageFormat;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("기본 할인 기능 테스트")
    void testDiscount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID().toString(), 100);
        assertThat(fixedAmountVoucher.discount(1000)).isEqualTo(900);
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
    void testWithMinus() {
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID().toString(), -100))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("음수는 될 수 없음");
    }

    @Test
    @DisplayName("할인 금액은 0이 될 수 없다.")
    void testWithZero() {
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID().toString(), 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("0이 될 수 없음");
    }

    @Test
    @DisplayName("할인 금액은 최대 금액을 넘을 수 없다.")
    void testWithMaximum() {
        long MAX_FIXED_VOUCHER_AMOUNT = 10000l;
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID().toString(), 10001))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("%d보다 작아야 함".formatted(MAX_FIXED_VOUCHER_AMOUNT));
    }
}