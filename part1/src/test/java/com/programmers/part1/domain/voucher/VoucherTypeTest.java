package com.programmers.part1.domain.voucher;

import com.programmers.part1.exception.voucher.VoucherTypeMissingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {

    @Test
    @DisplayName("올바른 바우처 타입을 반환하는 지")
    void testReturnVoucherType() {

        assertEquals(VoucherType.FIXED ,VoucherType.getVoucherType(1));
        assertEquals(VoucherType.PERCENT,VoucherType.getVoucherType(2));
        assertThrows(VoucherTypeMissingException.class, () -> VoucherType.getVoucherType(3));

    }
}