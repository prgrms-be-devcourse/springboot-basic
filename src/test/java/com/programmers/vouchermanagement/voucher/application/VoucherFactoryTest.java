package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherFactoryTest {

    @Test
    @DisplayName("정액 할인 바우처를 생성한다.")
    void createFixedAmountVoucher() {
        // given
        DiscountType discountType = DiscountType.FIX;
        int discountAmount = 10;

        // when
        Voucher result = VoucherFactory.createVoucher(discountType, discountAmount);

        // then
        assertThat(result).isInstanceOf(FixedAmountVoucher.class);
    }

    @Test
    @DisplayName("정률 할인 바우처를 생성한다.")
    void createPercentDiscountVoucher() {
        // given
        DiscountType discountType = DiscountType.PERCENT;
        int discountAmount = 10;

        // when
        Voucher result = VoucherFactory.createVoucher(discountType, discountAmount);

        // then
        assertThat(result).isInstanceOf(PercentDiscountVoucher.class);
    }
}