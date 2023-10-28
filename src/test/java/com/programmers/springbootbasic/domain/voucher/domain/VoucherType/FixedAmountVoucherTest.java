package com.programmers.springbootbasic.domain.voucher.domain.VoucherType;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_FIXED_VOUCHER_BENEFIT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {

    @DisplayName("유효한 데이터로 FixedAmountVoucher 인스턴스를 생성 할 수 있다.")
    @Test
    void success_createInstance() {
        // given
        Integer benefit = 10;

        // when
        var result = new FixedAmountVoucher(benefit);

        // then
        assertEquals(result.getVoucherTypeName(), "FIXED");
    }

    @DisplayName("benefit이 0 이하인 FixedAmountVoucher 인스턴스를 생성 할 수 없다.")
    @Test
    void fail_createInstance() {
        // given
        Integer benefit = -1;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(benefit))
            .isInstanceOf(VoucherException.class)
            .hasMessageContaining(INVALID_FIXED_VOUCHER_BENEFIT.getMessage());
    }

}
