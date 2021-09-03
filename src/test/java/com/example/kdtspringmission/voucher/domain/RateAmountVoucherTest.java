package com.example.kdtspringmission.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

public class RateAmountVoucherTest {

    @Test
    void testRateAmountVoucher() {
        //given
        Voucher voucherDiscount10 = new RateAmountVoucher(UUID.randomUUID(), 10L);
        Voucher voucherDiscount20 = new RateAmountVoucher(UUID.randomUUID(), 50L);

        //when
        long discount10 = voucherDiscount10.discount(10000L);
        long discount20 = voucherDiscount20.discount(10000L);

        //then
        assertThat(discount10).isEqualTo(9000L);
        assertThat(discount20).isEqualTo(5000L);
    }
}
