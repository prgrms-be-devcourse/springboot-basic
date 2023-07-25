package org.programmers.VoucherManagement.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PercentAmountVoucherTest {

    @Test
    @DisplayName("Percent 바우처를 생성한다. - 성공")
    void PercentAmountVoucher_Parameters_createPercentAmountVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        DiscountType discountType = DiscountType.PERCENT;
        DiscountValue discountValue = new DiscountValue(15);

        //when
        Voucher voucher = new PercentAmountVoucher(voucherId, discountType, discountValue);

        //then
        assertThat(voucher.getVoucherId()).isEqualTo(voucherId);
        assertThat(voucher.getDiscountType()).isEqualTo(discountType);
        assertThat(voucher.getDiscountValue()).isEqualTo(discountValue);
    }

    @Test
    @DisplayName("Percent 바우처를 생성한다. - 실패")
    void PercentAmountVoucher_Parameters_throwVoucherException() {
        //given&when
        UUID voucherId = UUID.randomUUID();
        DiscountType discountType = DiscountType.PERCENT;
        DiscountValue discountValue = new DiscountValue(101);

        //when
        assertThatThrownBy(() -> new PercentAmountVoucher(voucherId, discountType, discountValue))
                .isInstanceOf(VoucherException.class)
                .hasMessage("할인율은 1부터 100사이의 값이여야 합니다.");
    }
}
