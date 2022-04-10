package com.waterfogsw.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VoucherServiceTest {
    //바우처 서비스
    @Test
    @DisplayName("바우처 생성 - FixedAmount")
    public void createFixedAmountVoucher() {

    }

    @Test
    @DisplayName("바우처 생성 - PercentDiscount")
    public void createPercentAmountVoucher() {

    }

    @Test
    @DisplayName("FixedAmountVoucher - 음수인 경우")
    public void testAmountMinus() {

    }

    @Test
    @DisplayName("PercentAmountVoucher - 음수인 경우")
    public void testPercentMinus() {

    }

    @Test
    @DisplayName("PercentAmountVoucher - 100을 넘는 경우")
    public void testPercentHundred() {

    }

    @Test
    @DisplayName("존재하지 않는 메뉴인경우")
    public void menuCheck() {

    }

    @Test
    @DisplayName("존재하지 않는 바우처 타입인경우")
    public void voucherTypeCheck() {

    }
}
