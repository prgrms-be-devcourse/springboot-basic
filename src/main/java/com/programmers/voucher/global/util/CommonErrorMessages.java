package com.programmers.voucher.global.util;

public final class CommonErrorMessages {
    public static final String CANNOT_ACCESS_FILE = "Unable to access the file: Path {0}";

    private CommonErrorMessages() {
    }

    public static String addCurrentInput(String message, Object input) {
        return message + " Current input: " + input;
    }
}
