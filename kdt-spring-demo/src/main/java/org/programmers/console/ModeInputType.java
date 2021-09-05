package org.programmers.console;

import java.util.Arrays;

public enum ModeInputType {
    EXIT("exit"),
    CUSTOMER("customer"),
    VOUCHER("voucher");

    private String input;

    ModeInputType(String input) {
        this.input = input;
    }

    public static ModeInputType getModeInputType(String input) {
        return Arrays.stream(ModeInputType.values())
                .filter(modeInputType -> modeInputType.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong Mode Input"));
    }
}
