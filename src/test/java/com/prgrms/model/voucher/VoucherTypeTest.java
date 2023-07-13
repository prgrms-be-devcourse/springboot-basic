package com.prgrms.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTypeTest {

    private final String FIXEDVOUCHER = "1";
    private final String PERCENTVOUCER = "2";
    private final String STRANGEVOUCHER = "3";

    @Test
    @DisplayName("고정된 금액의 정책을 입력했을 때 FIXED_AMOUNT_VOUCHER의 VoucherType을 반환한다.")
    void findByPolicy_FixedVoucherPolicy_Equals() {
        //when
        VoucherType voucherPolicy = VoucherType.findByType(FIXEDVOUCHER);

        //then
        assertThat(voucherPolicy).isEqualTo(VoucherType.FIXED_AMOUNT_VOUCHER);
    }

    @Test
    @DisplayName("할인율 금액의 정책을 입력했을 때 PERCENT_DISCOUNT_VOUCHER의 VoucherType을 반환한다.")
    void findByPolicy_PercentVoucherPolicy_Equals() {
        //when
        VoucherType voucherPolicy = VoucherType.findByType(PERCENTVOUCER);

        //then
        assertThat(voucherPolicy).isEqualTo(VoucherType.PERCENT_DISCOUNT_VOUCHER);
    }

    @Test
    @DisplayName("존재하지 않은 할인 정책을 입력했을 때 예외를 던진다.")
    void findByPolicy_NotExistVoucherPolicy_Empty() {
        //when_then
        assertThatThrownBy(() -> VoucherType.findByType(STRANGEVOUCHER))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
