package org.prgrms.java.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.exception.VoucherException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentDiscountVoucherTest {
    @Test
    @DisplayName("할인 비율이 1~100 사이이면 정상적으로 바우처가 생성된다.")
    void testCreateVoucher() {
        assertDoesNotThrow(() -> {
            new PercentDiscountVoucher(UUID.randomUUID(), 1);
            new PercentDiscountVoucher(UUID.randomUUID(), 50);
            new PercentDiscountVoucher(UUID.randomUUID(), 100);
        });
    }

    @Test
    @DisplayName("할인 비율이 1 미만 100 초과이면 바우처가 생성될 수 없다.")
    void testCreateNegativeAmountVoucher() {
        assertThrows(VoucherException.class, () -> {
            new PercentDiscountVoucher(UUID.randomUUID(), -1);
            new PercentDiscountVoucher(UUID.randomUUID(), 101);
        });
    }
}