package me.programmers.springboot.basic.springbootbasic.voucher.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentAmountVoucherTest {

    @Test
    void discountTest() {
        Voucher percentAmountVoucher = new PercentAmountVoucher(UUID.randomUUID(), 10);

        long discountedPrice = percentAmountVoucher.discount(10000);

        assertThat(discountedPrice, is(9000L));
    }

}