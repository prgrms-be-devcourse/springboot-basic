package com.example.demo.view.validate;

import java.util.regex.Pattern;

public class CommandValidator {

    private static final Pattern IS_COMMAND_NUMBER = Pattern.compile("^[1-7]{1}$");

    private CommandValidator() {
    }

    public static void validateCommandNumber(String input) {
        if (!IS_COMMAND_NUMBER.matcher(input).matches()) {
            throw new IllegalArgumentException("[ERROR] 1~7 사이의 숫자를 입력해주세요.");
        }
    }
}
