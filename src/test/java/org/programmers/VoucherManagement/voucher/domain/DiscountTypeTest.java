package org.programmers.VoucherManagement.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DiscountTypeTest {
    @Test
    @DisplayName("입력값에 따라 DiscountType을 반환하는 테스트 - 성공")
    void from_InputTypeString_EqualsDiscountType() {
        String fixedInput = "fixed";
        String percentInput = "percent";

        DiscountType fixedTypeExpect = DiscountType.from(fixedInput);
        DiscountType percentTypeExpect = DiscountType.from(percentInput);
        assertThat(fixedTypeExpect).isEqualTo(DiscountType.FIXED);
        assertThat(percentTypeExpect).isEqualTo(DiscountType.PERCENT);
    }

    @Test
    @DisplayName("입력값에 따라 DiscountType을 반환하는 테스트 - 실패")
    void from_InputTypeString_ThrowVoucherException() {
        String input = "fixed123";

        assertThatThrownBy(() -> DiscountType.from(input))
                .isInstanceOf(VoucherException.class)
                .hasMessage("해당하는 유형의 바우처가 존재하지 않습니다.");
    }

}

