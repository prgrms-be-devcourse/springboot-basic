package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class VoucherTypeTest {

    @Test
    void Voucher_Type_입력() {
        //given
        String inputVoucherType = "fixed";
        String inputVoucherTypeTwo = "percent";
        //when
        VoucherType voucherType = VoucherType.of(inputVoucherType);
        VoucherType voucherTypeTwo = VoucherType.of(inputVoucherTypeTwo);
        //then
        Assertions.assertThat(voucherType).isEqualTo(VoucherType.FIXED);
        Assertions.assertThat(voucherTypeTwo).isEqualTo(VoucherType.PERCENT);
    }

    @Test
    void 잘못된_Voucher_Type_입력() {
        //given
        String inputVoucherType = "zero";
        //then
        Assertions.assertThatThrownBy(() -> VoucherType.of(inputVoucherType))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void Voucher_할인액_입력() {
        //given
        String inputAmount = "1000";
        VoucherType fixedType = VoucherType.FIXED;
        //when
        long valid = fixedType.isValid(inputAmount);
        //then
        Assertions.assertThat(valid).isEqualTo(1000);
    }

    @Test
    void 잘못된_범위의_Fixed_Voucher_할인액_입력() {
        //given
        String inputAmount = "100000";
        String inputAmountTwo = "-1";
        VoucherType fixedType = VoucherType.FIXED;
        //then
        Assertions.assertThatThrownBy(() -> fixedType.isValid(inputAmount))
                .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> fixedType.isValid(inputAmountTwo))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 잘못된_범위의_Percent_Voucher_할인율_입력() {
        //given
        String inputAmount = "1000";
        String inputAmountTwo = "-1";
        VoucherType percentType = VoucherType.PERCENT;
        //then
        Assertions.assertThatThrownBy(() -> percentType.isValid(inputAmount))
                .isInstanceOf(NumberFormatException.class);
        Assertions.assertThatThrownBy(() -> percentType.isValid(inputAmountTwo))
                .isInstanceOf(NumberFormatException.class);
    }

}