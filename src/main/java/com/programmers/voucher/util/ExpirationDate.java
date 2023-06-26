package com.programmers.voucher.util;

import java.time.LocalDateTime;

public class ExpirationDate {

    public static boolean checkExpirationDate(LocalDateTime createdDate, int expirationPolicy) {
        if (createdDate.plusDays(expirationPolicy).isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }
}
