package org.prgrms.kdt.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class VoucherValidatorTest {

    private final String FIXEDAMOUNTVOUCHER = "1";
    private final String PERCENTDISCOUNTVOUCHER = "2";

    @DisplayName("FixedAmountVoucher를 선택한 경우, 문자, 음수나 실수를 넣으면 예외가 발생")
    @ParameterizedTest
    @ValueSource(strings = {
            "10.4",
            "-40",
            "hi"
    })
    void 고정금액_할인정책_문자_음수_실수넣기(String discountAmount) {
        //then
        assertThrows(IllegalArgumentException.class,
                () -> VoucherValidator.validateVoucherStatusAndDiscountValue(FIXEDAMOUNTVOUCHER,discountAmount));
    }

    @DisplayName("PercentDiscountVoucher를 선택한 경우, 문자나 범위가 0과 같거나 더 적거나 100보다 큰 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {
            "101",
            "0",
            "-10"
    })
    void 퍼센트_할인정책_문자_범위벗어난값_넣기(String discountAmount) {
        //then
        assertThrows(IllegalArgumentException.class,
                () -> VoucherValidator.validateVoucherStatusAndDiscountValue(PERCENTDISCOUNTVOUCHER,discountAmount));
    }
}