package com.programmers.voucher.view;

public class ConsoleStyle {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";

    private ConsoleStyle() {}

    public static String bold(String text) {
        return BOLD + text + RESET;
    }
}
