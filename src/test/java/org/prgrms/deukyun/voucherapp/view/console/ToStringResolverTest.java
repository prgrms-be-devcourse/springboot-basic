package org.prgrms.deukyun.voucherapp.view.console;

import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.PercentDiscountVoucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.fixedAmountDiscountVoucher;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.percentDiscountVoucher;

class ToStringResolverTest {

    @Test
    void 성공_정액_할인_바우처_바우처_출력_문자열_변환() {
        //given
        FixedAmountDiscountVoucher voucher = fixedAmountDiscountVoucher();

        //when
        String voucherDisplayString = ToStringResolver.voucherToString(voucher);

        //then
        assertThat(voucherDisplayString).contains("[Fixed Amount Discount Voucher]");
        assertThat(voucherDisplayString).contains("amount  : " + voucher.getAmount());
    }

    @Test
    void 성공_정률_할인_바우처_출력_문자열_변환() {

        //given
        PercentDiscountVoucher voucher = percentDiscountVoucher();

        //when
        String voucherDisplayString = ToStringResolver.voucherToString(voucher);

        //then
        assertThat(voucherDisplayString).contains("[Percent Discount Voucher]     ");
        assertThat(voucherDisplayString).contains("percent : " + voucher.getPercent());
    }
}