package org.prgrms.kdtspringdemo.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FixedAmountVoucherTest {
    @Test
    @DisplayName("FixedAmountVoucher가 적용된 가격을 반환합니다.")
    public void discount(){

    }

    /**
     * 할인 금액 > 원래 금액
     */
    @Test
    @DisplayName("FixedAmountVoucher가 적용 된 금액이 마이너스가 될 수 없습니다.")
    public void discountWithGreaterThanBeforeDiscount(){

    }

    /**
     * 마이너스 제외
     */
    @Test
    @DisplayName("FixedAmountVoucher는 유효한 할인 금액으로만 생성할 수 있습니다.")
    public void discountWithInvalidValue(){

    }


}
