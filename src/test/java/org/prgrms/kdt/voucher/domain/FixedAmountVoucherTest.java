package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FixedAmountVoucherTest {

    @Test
    void discount() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID());

        //when
        double discountedPrice = fixedAmountVoucher.discount(100);

        //then
        assertThat(discountedPrice, is(80.0));
    }

}