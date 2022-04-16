package org.prgrms.kdtspringdemo.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FixedAmountVoucherTest {

    @Test
    @DisplayName("기존 금액에서 FixedAmountVoucher 적용된 가격을 반환합니다.")
    public void discount() {
    }

    @Test
    @DisplayName("FixedAmountVoucher 할인 금액이 마이너스 일 경우 예외가 발생합니다.")
    public void minusDiscountFixAmount() {
    }

    @Test
    @DisplayName("FixedAmountVoucher 할인 금액이 기존 금액보다 클 경우 0을 반환합니다.")
    public void discountWithGreaterDiscountAmountThanBeforeDiscount() {
    }
}
