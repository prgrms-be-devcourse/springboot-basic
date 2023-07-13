package org.prgrms.kdt.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    void percentDiscount(){
        //given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30);
        long beforePrice = 6000;

        //when
        long resultPrice = percentDiscountVoucher.discountAppliedPrice(beforePrice);

        //then
        assertThat(resultPrice).isEqualTo(4200);
    }

    @Test
    @DisplayName("소수점 금액은 불가하여 소수점 이하 버림")
    void percentDiscount2(){
        //given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 17);
        long beforePrice = 7648;

        //when
        long resultPrice = percentDiscountVoucher.discountAppliedPrice(beforePrice);

        //then
        assertThat(resultPrice).isEqualTo(6347);
    }

}