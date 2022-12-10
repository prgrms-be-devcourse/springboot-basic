package org.prgrms.java.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentDiscountVoucherTest {
    @Test
    @DisplayName("할인 비율이 1~100 사이이면 정상적으로 바우처가 생성된다.")
    void testCreateVoucher() {
        assertDoesNotThrow(() -> {
            PercentDiscountVoucher.builder()
                    .voucherId(UUID.randomUUID())
                    .ownerId(UUID.randomUUID())
                    .amount(1)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now())
                    .build();
            PercentDiscountVoucher.builder()
                    .voucherId(UUID.randomUUID())
                    .ownerId(UUID.randomUUID())
                    .amount(50)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now())
                    .build();
            PercentDiscountVoucher.builder()
                    .voucherId(UUID.randomUUID())
                    .ownerId(UUID.randomUUID())
                    .amount(100)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now())
                    .build();
        });
    }

    @Test
    @DisplayName("할인 비율이 1 미만 100 초과이면 바우처가 생성될 수 없다.")
    void testCreateNegativeAmountVoucher() {
        assertThrows(VoucherException.class, () -> {
            PercentDiscountVoucher.builder()
                    .voucherId(UUID.randomUUID())
                    .ownerId(UUID.randomUUID())
                    .amount(-1)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now())
                    .build();
            PercentDiscountVoucher.builder()
                    .voucherId(UUID.randomUUID())
                    .ownerId(UUID.randomUUID())
                    .amount(101)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now())
                    .build();
        });
    }
}