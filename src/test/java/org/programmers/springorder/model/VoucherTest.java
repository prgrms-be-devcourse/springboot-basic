package org.programmers.springorder.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {

    @Test
    @DisplayName("바우처 생성에 성공한다.")
    public void createVoucher() {
        //given
        UUID voucherId1 = UUID.randomUUID();
        long discountValue1 = 10;
        VoucherType voucherType1 = VoucherType.FIXED;
        long beforeDiscount = 10000;

        UUID voucherId2 = UUID.randomUUID();
        long discountValue2 = 10;
        VoucherType voucherType2 = VoucherType.PERCENT;

        //when
        Voucher voucher1 = Voucher.toVoucher(voucherId1, discountValue1, voucherType1);
        Voucher voucher2 = Voucher.toVoucher(voucherId2, discountValue2, voucherType2);

        //then
        assertThat(voucher1.getVoucherId()).isEqualTo(voucherId1);
        assertThat(voucher2.getVoucherId()).isEqualTo(voucherId2);

        assertThat(voucher1.getCalculate(beforeDiscount)).isEqualTo(9990);
        assertThat(voucher2.getCalculate(beforeDiscount)).isEqualTo(9000);
    }

    @Test
    @DisplayName("바우처 타입과 할인 금액 변경에 성공한다.")
    void updateVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        long discountValue = 10;
        VoucherType voucherType = VoucherType.FIXED;

        Voucher voucher = Voucher.toVoucher(voucherId, discountValue, voucherType);

        // when
        Voucher updatedVoucher = voucher.update(VoucherType.PERCENT, 20);

        // then
        assertThat(updatedVoucher.getVoucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(updatedVoucher.getDiscountValue()).isEqualTo(20);
    }
}