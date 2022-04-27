package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

class VoucherTypeTest {

    @Test
    void Voucher_Type_입력_성공() {
        //given
        String fixedType = "fixed";
        //when
        VoucherType voucherType = VoucherType.of(fixedType);
        //then
        Assertions.assertThat(voucherType).isEqualTo(VoucherType.FIXED);
    }

    @Test
    void Voucher_객체_생성() {
        Voucher voucher = VoucherType.of("FIXED").create(100L, VoucherType.FIXED, UUID.randomUUID());
        Assertions.assertThat(voucher).isNotNull();
    }

    @Test
    void 잘못된_Voucher_Type_입력_실패() {
        //given
        String inputVoucherType = "zero";
        //then
        Assertions.assertThatThrownBy(() -> VoucherType.of(inputVoucherType))
                .isInstanceOf(NoSuchElementException.class);
    }

}