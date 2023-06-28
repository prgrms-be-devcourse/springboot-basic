package com.programmers.voucher.global.util;

public interface CommonErrorMessages {
    String CANNOT_ACCESS_FILE = "Unable to access the file";

    static String currentInput(String message, Object input) {
        return message + " Current input: " + input;
    }

    static String filePath(String message, Object input) {
        return message + " File path: " + input;
    }
}
