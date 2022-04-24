package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class VoucherTypeTest {

    @Test
    void 성공_Voucher_Type_입력() {
        //given
        String fixedType = "fixed";
        //when
        VoucherType voucherType = VoucherType.of(fixedType);
        //then
        Assertions.assertThat(voucherType).isEqualTo(VoucherType.FIXED);
    }

    @Test
    void 실패_잘못된_Voucher_Type_입력() {
        //given
        String inputVoucherType = "zero";
        //then
        Assertions.assertThatThrownBy(() -> VoucherType.of(inputVoucherType))
                .isInstanceOf(NoSuchElementException.class);
    }

}