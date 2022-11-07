package com.programmers.voucher.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherFactoryTest {
    VoucherFactory voucherFactory = new VoucherFactory();

    @Test
    @DisplayName("타입별로 바우처를 생성한 뒤 타입 검사를 실시한다.")
    void 팩토리에서바우처생성() {
        Voucher fixVoucher = voucherFactory.createVoucher(VoucherList.FixedAmount, 2000);
        Voucher perVoucher = voucherFactory.createVoucher(VoucherList.PercentDiscount, 5);

        assertThat(fixVoucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(perVoucher).isInstanceOf(PercentDiscountVoucher.class);
    }
}