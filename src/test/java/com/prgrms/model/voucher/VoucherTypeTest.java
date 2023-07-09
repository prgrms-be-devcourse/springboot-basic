package com.prgrms.model.voucher;

import com.prgrms.model.voucher.dto.discount.Discount;
import com.prgrms.model.voucher.dto.discount.FixedDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTypeTest {
    @Test
    @DisplayName("고정된 금액의 정책을 입력했을 때 FIXED_AMOUNT_VOUCHER의 VoucherType을 반환한다.")
    void findByPolicy_FixedVoucherPolicy_Equals() {
        //given
        String policy = "1";

        //when
        VoucherType voucherPolicy = VoucherType.findByType(policy);

        //then
        assertThat(voucherPolicy).isEqualTo(VoucherType.FIXED_AMOUNT_VOUCHER);
    }

    @Test
    @DisplayName("할인율 금액의 정책을 입력했을 때 PERCENT_DISCOUNT_VOUCHER의 VoucherType을 반환한다.")
    void findByPolicy_PercentVoucherPolicy_Equals() {
        //given
        String policy = "2";

        //when
        VoucherType voucherPolicy = VoucherType.findByType(policy);

        //then
        assertThat(voucherPolicy).isEqualTo(VoucherType.PERCENT_DISCOUNT_VOUCHER);
    }

    @Test
    @DisplayName("존재하지 않은 할인 정책을 입력했을 때 예외를 던진다.")
    void findByPolicy_NotExistVoucherPolicy_Empty() {
        //given
        String policy = "FIXEDSDE";

        //when_then
        assertThatThrownBy(() -> VoucherType.findByType(policy))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("VocuherType을 통해 만든 고정된 금액의 바우처가 null이 아니고 고정된 금액인 바우처를 잘 생성했는지 확인한다.")
    void createVoucher_FixedVoucher_ReturnEqualInstanceOf() {
        // given
        int voucherId = 1;
        Discount discount = new FixedDiscount(10); // Example discount value

        // when
        Voucher voucher = VoucherType.FIXED_AMOUNT_VOUCHER.createVoucher(voucherId, discount);

        //then
        assertThat(voucher).isNotNull()
                .isInstanceOf(FixedAmountVoucher.class);
    }
}
