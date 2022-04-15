package org.prgrms.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VoucherServiceTest {

    @Test
    @DisplayName("PercentVoucher를 생성할때 percent가 100이 넘는경우 생성시 예외가 발생한다.")
    public void overPercentTest(){

    }

    @Test
    @DisplayName("PercentVoucher를 생성할때 percnet가 0보다 작으면 생성시 예외가 발생한다.")
    public void underPercentTest(){

    }

    @Test
    @DisplayName("FixedVoucher를 생성할 때 amount가 0보다 작으면 생성시 예외가 발생한다.")
    public void underAmountTest(){

    }

    @Test
    @DisplayName("Controller에게 받은 값으로 FixedAmountVoucher를 생성한다.")
    public void createFixedAmountVoucherTest(){

    }

    @Test
    @DisplayName("Controller에게 받은 값으로 PercentDiscountVoucher를 생성한다.")
    public void createPercentDiscountVoucherTest(){

    }
}
