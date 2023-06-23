package com.programmers.voucher.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountVoucherTest {
    public static final int PERCENT_DISCOUNT_AMOUNT = 10;
    public static final int ORIGINAL_AMOUNT = 1000;
    public static final int FINAL_AMOUNT = 900;

    private Voucher voucher;

    @BeforeEach
    void 초기화() {
        voucher = new PercentDiscountVoucher(UUID.randomUUID(), PERCENT_DISCOUNT_AMOUNT);
    }

    @Test
    void discount() {
        assertThat(voucher.discount(ORIGINAL_AMOUNT)).isEqualTo(FINAL_AMOUNT);
    }
}