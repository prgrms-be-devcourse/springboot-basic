package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class PercentAmountVoucherTest {
    private static long inputAmount;

    @BeforeAll
    void setUp() {
        inputAmount = 50L;
    }

    @Test
    void Percent_Voucher_주입() {
        //when
        PercentAmountVoucher voucher = new PercentAmountVoucher(inputAmount);
        //then
        Assertions.assertThat(voucher.getAmount()).isEqualTo(50);
    }

    @Test
    void 리스트를_위한_Percent_Voucher_주입() {
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        PercentAmountVoucher voucher = new PercentAmountVoucher(voucherId, inputAmount);
        //then
        Assertions.assertThat(voucher.getVoucherId()).isEqualTo(voucherId);
    }

}