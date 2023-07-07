package com.prgrms.model.voucher;

import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.FixedDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {
    @Test
    @DisplayName("find 고정된 금액의 정책")
    void findByPolicy_FixedVoucherPolicy_Equals() {
        //given
        String policy = "1";
        //when
        VoucherType voucherPolicy = VoucherType.findByPolicy(policy);
        //then
        assertThat(voucherPolicy).isEqualTo(VoucherType.FIXED_AMOUNT_VOUCHER);
    }

    @Test
    @DisplayName("find 할인율 정책")
    void findByPolicy_PercentVoucherPolicy_Equals() {
        //given
        String policy = "2";
        //when
        VoucherType voucherPolicy = VoucherType.findByPolicy(policy);
        //then
        assertThat(voucherPolicy).isEqualTo(VoucherType.PERCENT_DISCOUNT_VOUCHER);
    }

    @Test
    @DisplayName("없는 할인 정책에 대한 예외 테스트")
    void findByPolicy_NotExistVoucherPolicy_Empty() {
        //given
        String policy = "3";
        //when
        VoucherType voucherPolicy = VoucherType.findByPolicy(policy);
        //then
        assertThat(voucherPolicy).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("없는 할인 정책에 대한 예외 테스트")
    void createVoucher_() {
        // given
        Discount discount = new FixedDiscount(10); // Example discount value
        // when
        Voucher voucher = VoucherType.FIXED_AMOUNT_VOUCHER.createVoucher(discount);
        //then
        assertThat(voucher).isNotNull()
                .isInstanceOf(FixedAmountVoucher.class);
    }

}
