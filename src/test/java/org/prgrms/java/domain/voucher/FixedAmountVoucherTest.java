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
        assertDoesNotThrow(() -> FixedAmountVoucher.builder()
                .voucherId(UUID.randomUUID())
                .ownerId(UUID.randomUUID())
                .amount(10000)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now())
                .build());
    }

    @Test
    @DisplayName("금액이 0보다 작으면 고정 할인 바우처가 생성될 수 없다.")
    void testCreateNegativeAmountVoucher() {
        assertThrows(VoucherException.class, () -> FixedAmountVoucher.builder()
                .voucherId(UUID.randomUUID())
                .ownerId(UUID.randomUUID())
                .amount(-1)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now())
                .build());
    }
}