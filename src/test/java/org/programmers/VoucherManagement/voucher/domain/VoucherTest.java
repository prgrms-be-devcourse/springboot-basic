package org.programmers.VoucherManagement.voucher.domain;

import com.github.f4b6a3.ulid.UlidCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherTest {
    @Test
    @DisplayName("바우처의 할인 금액을 변경할 수 있다. - 성공")
    void changeDiscountValue_DiscountValue_Success() {
        //given
        Voucher voucher = new FixedAmountVoucher(UlidCreator.getUlid().toString(), DiscountType.FIXED, new DiscountValue(1000));

        //when
        voucher.changeDiscountValue(new DiscountValue(200));

        //then
        assertThat(voucher.getDiscountValue().getValue()).isEqualTo(200);
    }
}
