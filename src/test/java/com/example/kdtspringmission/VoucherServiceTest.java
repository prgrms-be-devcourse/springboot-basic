package com.example.kdtspringmission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.RateAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoucherServiceTest {

    VoucherService voucherService;

    @BeforeEach
    void beforeEach() {
        AppConfig ac = new AppConfig();
        voucherService = ac.voucherService();
    }

    @Test
    void testCreateVoucher() {
        Voucher fixedAmountVoucher = voucherService.create("1");
        Voucher rateAmountVoucher = voucherService.create("2");

        assertThat(fixedAmountVoucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(rateAmountVoucher).isInstanceOf(RateAmountVoucher.class);
    }

    @Test
    void testNoSuchVoucher() {
        assertThatThrownBy(
            () -> voucherService.create("NoNamedVoucher")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}