package com.programmers.kdtspringorder.voucher.factory;

import com.programmers.kdtspringorder.voucher.VoucherType;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class VoucherFactoryTest {

    @Test
    @DisplayName("타입에 맞는 바우처를 생성해야 한다.")
    void createVoucher() {
        // Given
        VoucherFactory voucherFactory = new VoucherFactory();

        // When
        Voucher voucher1 = voucherFactory.createVoucher(VoucherType.FIXED);
        Voucher voucher2 = voucherFactory.createVoucher(VoucherType.PERCENT);

        // Then
        assertThat(voucher1).isInstanceOf(FixedAmountVoucher.class);
        assertThat(voucher2).isInstanceOf(PercentDiscountVoucher.class);
    }
}