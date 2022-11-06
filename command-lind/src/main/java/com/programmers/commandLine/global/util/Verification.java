package com.programmers.commandLine.global.util;

import com.programmers.commandLine.global.factory.LoggerFactory;
import com.programmers.commandLine.global.io.Message;

public class Verification {
    public static Integer integerVerification(String input) {
        LoggerFactory.getLogger().info("Verification integerVerification 실행");

        try {
            return Integer.parseInt(input);
        } catch (RuntimeException e) {
            LoggerFactory.getLogger().error("Verification integerVerification 오류발생");
            throw new IllegalArgumentException(Message.DISCOUNT_ERROR.getMessage());
        }
    }
}

