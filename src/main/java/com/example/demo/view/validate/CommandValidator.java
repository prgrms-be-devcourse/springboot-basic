package com.example.demo.view.validate;

import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandValidator {

    private static final Pattern IS_NUMBER_ONE_TO_THREE = Pattern.compile("^[1-3]{1}$");
    private static final Pattern IS_NUMBER_ONE_TO_FOUR = Pattern.compile("^[1-4]{1}$");

    public static void validateCommandNumberOneToFour(String input) {
        if (!IS_NUMBER_ONE_TO_FOUR.matcher(input).matches()) {
            throw new IllegalArgumentException("[ERROR] 1~4 사이의 숫자를 입력해주세요.");
        }
    }

    public static void validateCommandNumberOneToThree(String input) {
        if (!IS_NUMBER_ONE_TO_THREE.matcher(input).matches()) {
            throw new IllegalArgumentException("[ERROR] 1~3 사이의 숫자를 입력해주세요.");
        }
    }
}
