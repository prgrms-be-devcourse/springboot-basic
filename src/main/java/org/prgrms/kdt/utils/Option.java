package org.prgrms.kdt.utils;


public enum Option {
    EXIT, LIST, CREATE, BLACKLIST;

    public static Option of(String input) {
        return java.util.Arrays.stream(values()).filter(option -> option.name().equals(input.toUpperCase())).findFirst().orElseThrow(() -> new RuntimeException("입력이 잘못되었습니다."));
    }

}
