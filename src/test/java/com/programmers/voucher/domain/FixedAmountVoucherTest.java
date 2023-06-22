package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountVoucherTest {
    
    @DisplayName("바우처 유효기간 테스트")
    @Test
    public void voucherAvailableTest() {
        UUID id = UUID.randomUUID();
        long amount = 10;

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(id, amount);
        assertTrue(fixedAmountVoucher.available());
    }

}