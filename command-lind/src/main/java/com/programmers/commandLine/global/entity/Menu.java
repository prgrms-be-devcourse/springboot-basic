package com.programmers.commandLine.global.entity;


public enum Menu {
    EXIT,
    CREATE,
    LIST,
    ERROR;

    private static final int BAD_REQUEST = -1;

    public static Menu selectMenu(String input) { // exit, create, list -> 123asfdewt
        String code = toCode(input);
        try {
            return Menu.valueOf(input);
        } catch (RuntimeException e) {
            return Menu.ERROR;
        }
    }

    private static String toCode(String input) {
        try {
            return input.toUpperCase(); //create, CREATE, Create 전부 정답이다.
        } catch (RuntimeException e) {
            return "ERROR";
        }
    }

}
