package com.programmers.commandLine.global.util;

import com.programmers.commandLine.global.io.Message;

public class Verification {
    public static Integer integerVerification(String input) {
        try {
            return Integer.parseInt(input);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(Message.DISCOUNT_ERROR.getMessage());
        }
    }
}

