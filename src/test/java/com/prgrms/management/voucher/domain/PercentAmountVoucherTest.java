package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentAmountVoucherTest {
    @Test
    void Percent_Voucher_주입() {
        //given
        long inputAmount = 50;
        //when
        PercentAmountVoucher voucher =new PercentAmountVoucher(inputAmount);
        //then
        Assertions.assertThat(voucher.getAmount()).isEqualTo(50);
    }

    @Test
    void 리스트를_위한_Percent_Voucher_주입() {
        //given
        long inputAmount = 50;
        UUID voucherId = UUID.randomUUID();
        //when
        PercentAmountVoucher voucher =new  PercentAmountVoucher(voucherId,inputAmount);
        //then
        Assertions.assertThat(voucher.getVoucherId()).isEqualTo(voucherId);
    }

}