package org.programmers.springbootbasic.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.Voucher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("정량 할인 바우처 테스트")
public class FixedAmountVoucherTest {

    @Test
    @DisplayName("기본 할인 기능 테스트")
    void discount() {
        Voucher voucher = new FixedDiscountVoucher(UUID.randomUUID(), 1000);
        long discountedValue = voucher.discount(10000);
        assertEquals(9000, discountedValue);
    }

}
