package com.programmers.voucher.domain;

import com.programmers.voucher.request.VoucherCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountVoucherTest {
    public static final int PERCENT_DISCOUNT_AMOUNT = 10;
    public static final int ORIGINAL_AMOUNT = 1000;
    public static final int FINAL_AMOUNT = 900;
    public static final String PERCENT_DISCOUNT_VOUCHER_TYPE = "percent";

    Voucher voucher;

    @BeforeEach
    void 초기화() {
        VoucherCreationRequest voucherCreationRequest = new VoucherCreationRequest(PERCENT_DISCOUNT_VOUCHER_TYPE, PERCENT_DISCOUNT_AMOUNT);
        voucher = new PercentDiscountVoucher(UUID.randomUUID(), voucherCreationRequest);
    }

    @Test
    void discount() {
        assertThat(voucher.discount(ORIGINAL_AMOUNT)).isEqualTo(FINAL_AMOUNT);
    }
}