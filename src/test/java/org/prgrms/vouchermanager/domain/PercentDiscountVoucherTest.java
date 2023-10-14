package org.prgrms.vouchermanager.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.voucher.PercentDiscountVoucher;

import java.util.UUID;

class PercentDiscountVoucherTest {

    @Test
    public void PercentDiscount(){
        //given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20L);
        //when
        long discountPrice = percentDiscountVoucher.discount(200L);
        //then
        Assertions.assertThat(discountPrice).isEqualTo(160L);
    }



}