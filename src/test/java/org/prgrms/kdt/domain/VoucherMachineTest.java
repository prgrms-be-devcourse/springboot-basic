package org.prgrms.kdt.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherMachineTest {

    final VoucherMachine voucherMachine = new VoucherMachine();

    @Test
    @DisplayName("Voucher 생성 테스트 - Fixed Type")
    void createFixedAmountVoucher() {
        VoucherType voucherType = VoucherType.valueOf("FIXED_AMOUNT");
        assertThat(voucherMachine.createVoucher(voucherType)).isNotNull();
    }

    @Test
    @DisplayName("Voucher 생성 테스트 - Percent Type")
    void createPercentDiscountVoucher() {
        VoucherType voucherType = VoucherType.valueOf("PERCENT_DISCOUNT");
        assertThat(voucherMachine.createVoucher(voucherType)).isNotNull();
    }
}
