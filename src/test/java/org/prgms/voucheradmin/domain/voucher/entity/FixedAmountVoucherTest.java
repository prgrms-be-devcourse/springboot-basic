package org.prgms.voucheradmin.domain.voucher.entity;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {
    @Test
    @DisplayName("고정 값 할인 테스트")
    void testFixedDiscount() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        long discountedAmount = voucher.discount(3000);
        assertThat(discountedAmount, is(2000L));
    }
}