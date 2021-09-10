package org.prgrms.kdtspringdemo.console;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.common.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@SpringJUnitConfig
@ContextConfiguration(classes = Config.class)
class VoucherOperatorTest{
    @Autowired
    VoucherOperator voucherOperator;
    @Test
    @DisplayName("FixedAmountVoucher 생성하기")
    void createFixedAmountVoucher() {
        var voucher = voucherOperator.create("F 99".split(" "));
        var voucherUp = voucherOperator.create("F 100050".split(" "));
        var voucherDown = voucherOperator.create("F 0".split(" "));

        assertThat(voucher.getDiscount(), is(99L));
        assertThat(voucherUp, is(nullValue()));
        assertThat(voucherDown, is(nullValue()));
    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성하기")
    void createPercentDiscountVoucher() {
        var voucher = voucherOperator.create("P 99".split(" "));
        var voucherUp = voucherOperator.create("P 105".split(" "));
        var voucherDown = voucherOperator.create("P 0".split(" "));

        assertThat(voucher.getDiscount(), is(99L));
        assertThat(voucherUp, is(nullValue()));
        assertThat(voucherDown, is(nullValue()));
    }

    @Test
    @DisplayName("Voucher 커맨드 작동 확인")
    void checkCommand() {
        boolean percentCheck = voucherOperator.validationCheck("P 3".split(" "));
        boolean fixCheck = voucherOperator.validationCheck("F 3".split(" "));
        boolean noneTypeCheck = voucherOperator.validationCheck("AAA 13".split(" "));
        boolean numValueCheck = voucherOperator.validationCheck("AAA BB".split(" "));
        boolean argCountCheck = voucherOperator.validationCheck("AAA 33 CC".split(" "));

        assertThat(percentCheck, is(true));
        assertThat(fixCheck, is(true));
        assertThat(noneTypeCheck, is(false));
        assertThat(numValueCheck, is(false));
        assertThat(argCountCheck, is(false));
    }
}