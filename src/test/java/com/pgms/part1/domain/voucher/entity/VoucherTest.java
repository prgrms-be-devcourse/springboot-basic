package com.pgms.part1.domain.voucher.entity;

import com.pgms.part1.exception.VoucherApplicationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTest {

    @Test
    void voucher_생성_discount범위_실패(){
        // expected
        assertThrows(VoucherApplicationException.class, () -> Voucher.newVocher(1L, 10000000, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT));
        assertThrows(VoucherApplicationException.class, () -> Voucher.newVocher(1L, 10000000, VoucherDiscountType.PERCENT_DISCOUNT));
    }
}
