package com.programmers.commandLine.global.entity;


import com.programmers.commandLine.global.factory.LoggerFactory;

public enum Menu {
    EXIT,
    VOUCHER_CREATE,
    VOUCHER_LIST,
    BLACK_CONSUMER_LIST,
    ERROR;

    private static final int BAD_REQUEST = -1;

    public static Menu selectMenu(String input) { // exit, create, list -> 123asfdewt
        LoggerFactory.getLogger().info("Menu selectMenu 실행");
        String code = toCode(input);
        try {
            return Menu.valueOf(code);
        } catch (RuntimeException e) {
            return Menu.ERROR;
        }
    }

    private static String toCode(String input) {
        LoggerFactory.getLogger().info("Menu toCode 실행");
        try {
            return input.toUpperCase(); //create, CREATE, Create 전부 정답이다.
        } catch (RuntimeException e) {
            return "ERROR";
        }
    }

}
