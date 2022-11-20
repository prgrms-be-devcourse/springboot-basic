package org.prgrms.java.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {
    @Test
    @DisplayName("금액이 0보다 크면 정상적으로 고정 할인 바우처가 생성된다.")
    void testCreateVoucher() {
        assertDoesNotThrow(() -> new FixedAmountVoucher(UUID.randomUUID(), 10000, LocalDateTime.now()));
    }

    @Test
    @DisplayName("금액이 0보다 작으면 고정 할인 바우처가 생성될 수 없다.")
    void testCreateNegativeAmountVoucher() {
        assertThrows(VoucherException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -1, LocalDateTime.now()));
    }
}