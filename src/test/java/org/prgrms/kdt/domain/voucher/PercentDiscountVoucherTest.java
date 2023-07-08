package org.prgrms.kdt.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("바우처 할인 퍼센트로 100 이상을 입력할 경우 예외가 발생한다.")
    public void test_Percent_Discount() {
        //given
        Long id = 1L;
        Long discount = 120L;

        // when & then
        assertThatThrownBy(() -> new PercentDiscountVoucher(id,discount))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("할인 퍼센트는 100 미만이어야 합니다.");
    }

    @Test
    @DisplayName("바우처 할인 퍼센트로 0을 입력할 경우 예외가 발생한다.")
    public void test_Percent_Discount_Zero() {
        //given
        Long id = 1L;
        Long discount = 0L;

        // when & then
        assertThatThrownBy(() -> new PercentDiscountVoucher(id,discount))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("할인 퍼센트는 0이상이어야 합니다.");
    }

    @Test
    @DisplayName("바우처 할인 퍼센트로 음수를 입력할 경우 예외가 발생한다.")
    public void test_Percent_Discount_Minus() {
        //given
        Long id = 1L;
        Long discount = -10L;

        // when & then
        assertThatThrownBy(() -> new PercentDiscountVoucher(id,discount))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("할인 퍼센트는 양수여야 합니다.");
    }

}