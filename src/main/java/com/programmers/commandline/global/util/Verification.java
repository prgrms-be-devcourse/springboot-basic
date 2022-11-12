package com.programmers.commandline.global.util;

import com.programmers.commandline.global.factory.LoggerFactory;
import com.programmers.commandline.global.io.Message;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

public class Verification {

    private Verification() {}

    public static void validateParseToNumber(String input) {

        Pattern numberPattern = Pattern.compile("^[0-9]*$");
        if (!numberPattern.matcher(input).matches()) {
            throw new IllegalArgumentException("잘못된 입력값입니다 숫자여야합니다 ");
        }
    }
}
