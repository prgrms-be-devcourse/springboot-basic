package com.programmers.springbootbasic.domain.voucher.domain.VoucherType;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_PERCENT_VOUCHER_BENEFIT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.programmers.springbootbasic.exception.exceptionClass.VoucherException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentDiscountVoucherTest {

    @DisplayName("유효한 데이터로 PercentDiscountVoucher 인스턴스를 생성 할 수 있다.")
    @Test
    void success_createInstance() {
        // given
        Integer benefit = 10;

        // when
        var result = new PercentDiscountVoucher(benefit);

        // then
        assertEquals(result.getVoucherTypeName(), "PERCENT");
    }

    @DisplayName("benefit이 0 이하 100 초과인 PercentDiscountVoucher 인스턴스를 생성 할 수 없다.")
    @Test
    void fail_createInstance() {
        // given
        Integer benefit1 = -1;
        Integer benefit2 = 101;

        // when && then
        assertThatThrownBy(() -> new PercentDiscountVoucher(benefit1))
            .isInstanceOf(VoucherException.class)
            .hasMessageContaining(INVALID_PERCENT_VOUCHER_BENEFIT.getMessage());

        assertThatThrownBy(() -> new PercentDiscountVoucher(benefit2))
            .isInstanceOf(VoucherException.class)
            .hasMessageContaining(INVALID_PERCENT_VOUCHER_BENEFIT.getMessage());
    }

}
