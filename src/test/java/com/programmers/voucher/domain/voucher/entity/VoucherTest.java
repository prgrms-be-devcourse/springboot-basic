package com.programmers.voucher.domain.voucher.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class VoucherTest {
    @Test
    @DisplayName("할인율이 100 초과인 경우 예외가 발생한다.")
    void 퍼센트_할인_바우처_생성_예외() {
        assertThatThrownBy(() -> Voucher.create(VoucherType.PERCENT, 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 100을 넘을 수 없습니다.");
    }
}
