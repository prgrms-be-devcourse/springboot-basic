package com.prgrms.management.voucher.domain;

import com.prgrms.management.command.Command;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

}