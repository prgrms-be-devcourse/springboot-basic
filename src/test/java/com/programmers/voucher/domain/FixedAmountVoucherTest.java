package com.programmers.voucher.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountVoucherTest {

    public static final int FIXED_DISCOUNT_AMOUNT = 100;
    public static final int ORIGINAL_AMOUNT = 1000;
    public static final int FINAL_AMOUNT = 900;

    Voucher voucher;

    @BeforeEach
    void 초기화() {
        voucher = new FixedAmountVoucher(UUID.randomUUID(), FIXED_DISCOUNT_AMOUNT);
    }

    @Test
    void discount() {
        assertThat(voucher.discount(ORIGINAL_AMOUNT)).isEqualTo(FINAL_AMOUNT);
    }
}