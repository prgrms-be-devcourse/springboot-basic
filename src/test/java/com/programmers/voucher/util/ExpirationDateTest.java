package com.programmers.voucher.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExpirationDateTest {

    @DisplayName("생성일은 현재로부터 한 달전, 유효기간 정책은 일주일인 경우")
    @Test
    void voucherExpirationDateTest() {
        int expirationPolicy = 7;

        LocalDateTime createdDate = LocalDateTime.now().minusMonths(1);

        assertFalse(ExpirationDate.checkExpirationDate(createdDate, expirationPolicy));
    }
}
