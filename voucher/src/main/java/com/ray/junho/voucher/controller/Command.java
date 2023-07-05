package com.ray.junho.voucher.controller;

import java.util.Arrays;

public enum Command {
    EXIT,CREATE, LIST;

    public static Command find(String input) {
        String inputToUpperCase = input.toUpperCase();

        return Arrays.stream(values())
                .filter(command -> command.toString().equals(inputToUpperCase))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 명령어가 존재하지 않습니다."));
    }
}
