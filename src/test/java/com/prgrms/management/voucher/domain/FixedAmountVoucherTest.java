package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class FixedAmountVoucherTest {

    private static long inputAmount;

    @BeforeAll
    void setUp() {
        inputAmount = 1000L;
    }

    @Test
    void Fixed_Voucher_주입() {
        //when
        FixedAmountVoucher voucher = new FixedAmountVoucher(inputAmount);
        //then
        Assertions.assertThat(voucher.getAmount()).isEqualTo(1000L);
    }

    @Test
    void 리스트를_위한_Fixed_Voucher_주입() {
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId, inputAmount);
        //then
        Assertions.assertThat(voucher.getVoucherId()).isEqualTo(voucherId);
    }
}