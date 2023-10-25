package org.prgrms.vouchermanager.domain.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.VoucherType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("percent 할인이 잘 적용되는 지 테스트")
    public void PercentDiscount(){
        //given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20, VoucherType.PERCENT);
        //when
        long discountPrice = percentDiscountVoucher.discount(200L);
        //then
        assertThat(discountPrice).isEqualTo(160L);
    }

    @Test
    @DisplayName("할인율을 적용했을 때 결과금액이 0원이하면 최종금액으로 0원이 반환되어야 한다.")
    void minusDiscountPrice(){
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 200, VoucherType.PERCENT);

        long discountPrice = percentDiscountVoucher.discount(100L);

        assertThat(discountPrice).isEqualTo(0L);
    }


}