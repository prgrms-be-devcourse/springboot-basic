package com.prgrms.model.voucher;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherTypeTest {

    private final String strangeVoucher = "3";

    @Test
    @DisplayName("존재하지 않은 할인 정책을 입력했을 때 예외를 던진다.")
    void findByPolicy_NotExistVoucherPolicy_Empty() {
        //when_then
        assertThatThrownBy(() -> VoucherType.findByType(strangeVoucher))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
