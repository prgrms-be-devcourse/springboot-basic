package org.programmers.springbootbasic.voucher.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@DisplayName("비율 할인 바우처 테스트")
class RateDiscountVoucherTest {

    @Test
    @DisplayName("기본 할인 기능 테스트")
    void discount() {
        Voucher voucher = new RateDiscountVoucher(UUID.randomUUID(), 15);
        long discountedValue = voucher.discount(10000);
        Assertions.assertEquals(8500, discountedValue);
    }
}