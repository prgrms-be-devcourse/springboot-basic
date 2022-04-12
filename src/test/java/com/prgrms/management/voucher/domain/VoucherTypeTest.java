package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class VoucherTypeTest {

    @Test
    void Fixed_Voucher_Type_입력() {
        //given
        String fixedType = "fixed";
        //when
        VoucherType voucherType = VoucherType.of(fixedType);
        //then
        Assertions.assertThat(voucherType).isEqualTo(VoucherType.FIXED);
    }

    @Test
    void Percent_Voucher_Type_입력() {
        //given
        String percentType = "percent";
        //when
        VoucherType voucherType = VoucherType.of(percentType);
        //then
        Assertions.assertThat(voucherType).isEqualTo(VoucherType.PERCENT);
    }

    @Test
    void 잘못된_Voucher_Type_입력() {
        //given
        String inputVoucherType = "zero";
        //then
        Assertions.assertThatThrownBy(() -> VoucherType.of(inputVoucherType))
                .isInstanceOf(NoSuchElementException.class);
    }

}