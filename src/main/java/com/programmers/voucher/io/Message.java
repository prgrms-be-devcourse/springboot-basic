package com.programmers.voucher.io;

public enum Message {
    INTRO_MESSAGE("=== Voucher Program ===\n" +
            "    Type **exit** to exit the program.\n" +
            "    Type **create** to create a new voucher.\n" +
            "    Type **list** to list all vouchers.");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
