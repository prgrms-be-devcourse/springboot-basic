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

}