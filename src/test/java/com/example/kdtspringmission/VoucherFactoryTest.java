package com.example.kdtspringmission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VoucherFactoryTest {

    @Test
    void testCreateVoucher() {
        Voucher fixedAmountVoucher = VoucherFactory.create("FixedAmountVoucher");
        Voucher rateAmountVoucher = VoucherFactory.create("RateAmountVoucher");

        assertThat(fixedAmountVoucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(rateAmountVoucher).isInstanceOf(RateAmountVoucher.class);
    }

    @Test
    void testNoSuchVoucher() {
        assertThatThrownBy(
            () -> VoucherFactory.create("NoNamedVoucher")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}