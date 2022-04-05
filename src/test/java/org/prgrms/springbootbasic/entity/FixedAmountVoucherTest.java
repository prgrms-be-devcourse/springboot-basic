package org.prgrms.springbootbasic.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {

    @DisplayName("FixedAmountVoucher 테스트")
    @Test
    void test() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 10L;

        //when
        var voucher = new FixedAmountVoucher(voucherId, amount);

        //then
        Assertions.assertAll(
            () -> assertEquals(voucherId, voucher.getVoucherId()),
            () -> assertEquals(90L, voucher.discount(100L))
        );
    }

}