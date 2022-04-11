package org.prgrms.kdtspringdemo.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PercentDiscountVoucherTest {
    @Test
    @DisplayName("PercentDiscountVoucher가 적용된 가격을 반환합니다.")
    public void discount(){

    }

    /**
     * 마이너스 제외, 100을 넘는 숫자 제외, ..
     */
    @Test
    @DisplayName("PercentDiscountVoucher는 유효한 할인 금액으로만 생성할 수 있습니다.")
    public void discountWithInvalidValue(){

    }
}
