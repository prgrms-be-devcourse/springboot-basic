package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PercentDiscountVoucherTest {
    @Test
    public void discount테스트() {
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID());

        double discountPrice = percentDiscountVoucher.discount(150);

        assertThat(discountPrice, is(120.0));
    }
}