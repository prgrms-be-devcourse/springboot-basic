package org.prgrms.kdt.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.voucher.WrongRangeInputException;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("percent는 0보다 작은 값이 들어 올 수 없다.")
    void validateMinTest() {
        assertThrows(WrongRangeInputException.class, () -> {
            Voucher voucher = new PercentDiscountVoucher(1L, -1);
        });
    }

    @Test
    @DisplayName("percent는 100보다 큰 값이 들어 올 수 없다.")
    void validateMaxTest() {
        assertThrows(WrongRangeInputException.class, () -> {
            Voucher voucher = new PercentDiscountVoucher(1L, 101);
        });
    }
}