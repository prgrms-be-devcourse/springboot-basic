package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PercentDiscountVoucherTest {
    @Test
    void discount() {
        //given
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID());

        //when
        double discountedPrice = percentDiscountVoucher.discount(150);

        //then
        assertThat(discountedPrice, is(120.0));
    }
}