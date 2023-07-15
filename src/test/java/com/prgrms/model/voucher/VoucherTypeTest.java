package com.prgrms.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTypeTest {

    private final String STRANGEVOUCHER = "3";

    @Test
    @DisplayName("존재하지 않은 할인 정책을 입력했을 때 예외를 던진다.")
    void findByPolicy_NotExistVoucherPolicy_Empty() {
        //when_then
        assertThatThrownBy(() -> VoucherType.findByType(STRANGEVOUCHER))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
