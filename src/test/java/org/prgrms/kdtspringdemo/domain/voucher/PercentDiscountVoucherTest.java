package org.prgrms.kdtspringdemo.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PercentDiscountVoucherTest {

    @Test
    @DisplayName("기존 금액에서 PercentDiscountVoucher 적용된 가격을 반환합니다.")
    public void discount() {
    }

    @Test
    @DisplayName("PercentDiscountVoucher 할인율이 마이너스 일 경우 예외가 발생합니다.")
    public void minusDiscountPercent() {
    }

    @Test
    @DisplayName("PercentDiscountVoucher 할인율이 100을 넘는다면 예외가 발생합니다.")
    public void discountPercentOver100() {
    }
}
