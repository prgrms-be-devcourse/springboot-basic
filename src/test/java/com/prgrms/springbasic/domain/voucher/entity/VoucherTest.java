package com.prgrms.springbasic.domain.voucher.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {

    @Test
    @DisplayName("voucher 타입별 생성 테스트")
    void testCreateVoucher() {
        Voucher fixedVoucher = Voucher.createVoucher(UUID.randomUUID(), "fixed", 50);
        Voucher percentVoucher = Voucher.createVoucher(UUID.randomUUID(), "percent", 50);

        assertThat(fixedVoucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(percentVoucher).isInstanceOf(PercentDiscountVoucher.class);
    }

    @Test
    @DisplayName("percent 타입의 discountValue는 0부터 100 사이의 값이어야 한다")
    void testPercentVoucherCreationFail() {
        assertThatThrownBy(() -> Voucher.createVoucher(UUID.randomUUID(), "percent", 150))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
