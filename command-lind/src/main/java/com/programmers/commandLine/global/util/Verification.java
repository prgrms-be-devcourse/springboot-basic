package com.programmers.commandLine.global.util;

import com.programmers.commandLine.global.io.Message;

public class Verification {
    public static Integer discountVerification(String discount) {
        try {
            return Integer.parseInt(discount);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(Message.DISCOUNT_ERROR.getMessage());
        }
    }
}

