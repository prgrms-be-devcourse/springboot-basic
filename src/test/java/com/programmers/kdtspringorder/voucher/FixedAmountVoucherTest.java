package com.programmers.kdtspringorder.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    public void discount() throws Exception{
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 30L);

        //when
        long amount = fixedAmountVoucher.discount(100L);

        //then
        Assertions.assertThat(amount).isEqualTo(70L);
    }
}