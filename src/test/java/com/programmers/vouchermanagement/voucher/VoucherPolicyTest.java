package com.programmers.vouchermanagement.voucher;

import com.programmers.vouchermanagement.voucher.domain.PercentVoucherPolicy;
import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VoucherPolicyTest {

    @Test
    @DisplayName("VoucherPolicy 생성 시 잘못된 할인 범위는 예외를 발생시킨다.")
    void testIllegalDiscountException() {

        // given
        Long discount = 150L;

        // when - then
        assertThatThrownBy(() -> new PercentVoucherPolicy(discount))
                .isInstanceOf(IllegalDiscountException.class);
    }

}
