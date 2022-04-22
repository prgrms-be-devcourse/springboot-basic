package com.prgrms.vouchermanagement.voucher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.prgrms.vouchermanagement.voucher.VoucherType.FIXED_DISCOUNT;
import static com.prgrms.vouchermanagement.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.assertj.core.api.Assertions.*;

class VoucherTest {

    @Test
    @DisplayName("Voucher를 생성한다.")
    void createVoucherTest() {
        UUID voucherId = UUID.randomUUID();
        int amount = 50;
        LocalDateTime createdAt = LocalDateTime.now();

        Voucher voucher = PERCENT_DISCOUNT.constructor(voucherId, amount, createdAt);

        assertThat(voucher.getVoucherId()).isEqualTo(voucherId);
        assertThat(voucher.getAmount()).isEqualTo(amount);
        assertThat(voucher.getCreatedAt()).isEqualTo(createdAt);
    }

    @ParameterizedTest
    @ValueSource(ints = {999, -100})
    @DisplayName("PercentVoucher 를 생성할 때 0~100 범위가 아닌 amount 값을 입력하면 예외 발생")
    void percentVoucherDiscountText(int amount) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> PERCENT_DISCOUNT.constructor(UUID.randomUUID(), amount, LocalDateTime.now()));
    }

    @ParameterizedTest
    @ValueSource(ints = {-200, -100})
    @DisplayName("FixedVoucher 를 생성할 때 0보다 작은 amount 값을 입력하면 예외 발생")
    void fixedVoucherDiscountText(int amount) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> FIXED_DISCOUNT.constructor(UUID.randomUUID(), amount, LocalDateTime.now()));
    }
}