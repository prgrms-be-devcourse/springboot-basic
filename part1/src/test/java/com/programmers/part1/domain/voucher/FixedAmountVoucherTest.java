package com.programmers.part1.domain.voucher;

import com.programmers.part1.domain.voucher.FixedAmountVoucher;
import com.programmers.part1.domain.voucher.Voucher;
import com.programmers.part1.exception.voucher.FixedAmountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("할인이 올바르게 적용되는 지")
    void testDiscount() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(),100);
        assertEquals(900, voucher.discount(1000));
        assertNotEquals(1000, voucher.discount(1000));
    }

    @Test
    @DisplayName("할인 금액이 0보다 큰 지")
    void testDiscountAmountPositive(){
        assertAll( "FixedAmountVoucher creation",
                () -> assertThrows(FixedAmountException.class, () -> new FixedAmountVoucher(UUID.randomUUID(),0)),
                () -> assertThrows(FixedAmountException.class, () -> new FixedAmountVoucher(UUID.randomUUID(),-1))
        );
    }
}