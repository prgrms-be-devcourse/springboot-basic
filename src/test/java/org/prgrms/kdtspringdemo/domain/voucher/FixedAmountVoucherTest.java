package org.prgrms.kdtspringdemo.domain.voucher;

import org.junit.jupiter.api.DisplayName;

public class FixedAmountVoucherTest {

    @DisplayName("기존 금액에서 FixedAmountVoucher 적용된 가격을 반환합니다.")
    public void discount() {
    }

    @DisplayName("FixedAmountVoucher 할인 금액이 마이너스 일 경우 예외가 발생합니다.")
    public void minusDiscountFixAmount() {
    }

    @DisplayName("FixedAmountVoucher 할인 금액이 기존 금액보다 클 경우 0을 반환합니다.")
    public void discountWithGreaterDiscountAmountThanBeforeDiscount() {
    }
}