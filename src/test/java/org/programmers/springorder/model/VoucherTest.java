package org.programmers.springorder.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {

    @ParameterizedTest
    @CsvSource(value = {"FIXED, 1000, 10000, 9000", "PERCENT, 10, 10000, 9000"})
    @DisplayName("바우처 생성에 성공한다.")
    public void createVoucher(VoucherType voucherType, long discountValue, long beforeDiscount, long expectedAfterDiscount) {
        //given
        UUID voucherId = UUID.randomUUID();


        //when
        Voucher voucher = Voucher.toVoucher(voucherId, discountValue, voucherType);

        //then
        assertThat(voucher.getVoucherId()).isEqualTo(voucherId);
        assertThat(voucher.getCalculate(beforeDiscount)).isEqualTo(expectedAfterDiscount);
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
        voucher.update(VoucherType.PERCENT, 20);

        // then
        assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(voucher.getDiscountValue()).isEqualTo(20);
    }
}