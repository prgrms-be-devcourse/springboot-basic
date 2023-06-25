package com.programmers.voucher.constant;

public class Style {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";

    private Style() {}

    //TODO 구조 고민 -> style enum 변경?
    public static String apply(String text, String style) {
        return style + text + RESET;
    }
}
