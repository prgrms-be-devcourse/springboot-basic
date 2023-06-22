package com.programmers.voucher.domain;

import com.programmers.voucher.request.VoucherCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountVoucherTest {
    public static final int FIXED_DISCOUNT_AMOUNT = 100;
    public static final int ORIGINAL_AMOUNT = 1000;
    public static final int FINAL_AMOUNT = 900;
    public static final String FIXED_AMOUNT_VOUCHER_TYPE = "fixed";

    Voucher voucher;

    @BeforeEach
    void 초기화() {
        VoucherCreationRequest voucherCreationRequest = new VoucherCreationRequest(FIXED_AMOUNT_VOUCHER_TYPE, FIXED_DISCOUNT_AMOUNT);
        voucher = new FixedAmountVoucher(UUID.randomUUID(), voucherCreationRequest);
    }

    @Test
    void discount() {
        assertThat(voucher.discount(ORIGINAL_AMOUNT)).isEqualTo(FINAL_AMOUNT);
    }
}