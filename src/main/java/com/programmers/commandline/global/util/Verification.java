package com.programmers.commandline.global.util;

import com.programmers.commandline.global.io.Message;

import java.util.regex.Pattern;

public class Verification {


    private Verification() {
    }

    public static final void validateParseToInt(String input) {
        Pattern numberPattern = Pattern.compile("^[0-9]*$");
        if (!numberPattern.matcher(input).matches()) {
            throw new IllegalArgumentException(Message.VALIDATE_PARSE_TO_NUMBER_ERROR.getMessage());
        }
    }

}
