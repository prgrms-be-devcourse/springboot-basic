package org.prgrms.kdt.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.utils.VoucherType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("바우처 할인 가격으로 음수를 입력할 경우 예외가 발생한다.")
    public void test_Fixed_Discount() {
        //given
        Long id = 1L;
        Long discount = -100L;

        // when & then
        assertThatThrownBy(() -> new FixedAmountVoucher(id,discount))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("할인 금액은 양수여야 합니다.");
    }


}