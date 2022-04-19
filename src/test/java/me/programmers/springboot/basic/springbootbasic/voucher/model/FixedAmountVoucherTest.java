package me.programmers.springboot.basic.springbootbasic.voucher.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    void discountTest() {
        int discountAmount = 1000;
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);

        long discountedPrice = voucher.discount(10000);

        assertThat(discountedPrice, is(9000L));
    }

}