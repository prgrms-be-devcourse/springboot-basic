package com.programmers.voucher.global.util;

public final class CommonErrorMessages {
    public static final String CANNOT_ACCESS_FILE = "Unable to access the file";

    private CommonErrorMessages() {
    }

    public static String addCurrentInput(String message, Object input) {
        return message + " Current input: " + input;
    }

    public static String addFilePath(String message, Object input) {
        return message + " File path: " + input;
    }
}
