package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.voucher.PercentDiscountVoucher;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    public void discount() throws Exception{
        //given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20L);

        //when
        long discount = percentDiscountVoucher.discount(100);

        //then
        assertThat(discount).isEqualTo(80L);
    }
}