package com.example.kdtspringmission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.RateAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.domain.VoucherFactory;
import org.junit.jupiter.api.Test;

class VoucherFactoryTest {

    @Test
    void testCreateVoucher() {
        Voucher fixedAmountVoucher = VoucherFactory.create("1");
        Voucher rateAmountVoucher = VoucherFactory.create("2");

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