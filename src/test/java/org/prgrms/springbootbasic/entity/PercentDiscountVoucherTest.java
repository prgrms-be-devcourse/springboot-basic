package org.prgrms.springbootbasic.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentDiscountVoucherTest {

    @DisplayName("PercentDiscountVoucher 테스트")
    @Test
    void test() {
        //given
        UUID voucherId = UUID.randomUUID();
        int percent = 10;

        //when
        var voucher = new PercentDiscountVoucher(voucherId, percent);

        //then
        Assertions.assertAll(
            () -> Assertions.assertEquals(voucherId, voucher.getVoucherId()),
            () -> Assertions.assertEquals(90L, voucher.discount(100L))
        );
    }
}