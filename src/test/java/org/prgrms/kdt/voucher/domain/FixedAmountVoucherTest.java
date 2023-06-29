package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FixedAmountVoucherTest {

    @Test
    public void discount테스트() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID());

        double discountPrice = fixedAmountVoucher.discount(100);

        assertThat(discountPrice, is(80.0));
    }

}