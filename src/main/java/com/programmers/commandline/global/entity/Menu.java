package com.programmers.commandline.global.entity;

import com.programmers.commandline.global.io.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum Menu {


    EXIT(1),
    VOUCHER_CREATE(2),
    VOUCHER_LIST(3),
    BLACK_CONSUMER_LIST(4),
    CONSUMER(5);

    private static final Logger logger = LoggerFactory.getLogger(Menu.class);
    private final int code;

    Menu(int code) {
        this.code = code;
    }

    public static Menu ofMenu(String input) {

        int code = toCode(input);

        return Arrays.stream(Menu.values())
                .filter(menu -> menu.code == code)
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("메뉴 생성에 검증되지 않는 에러가 있습니다.");
                    throw new IllegalArgumentException("잘못된 메뉴 선택입니다.");
                });
    }

    private static int toCode(String input) {
        validateCode(input);
        return Integer.parseInt(input);
    }

    private static void validateCode(String code) {
        Pattern numberPattern = Pattern.compile("^[0-9]*$");
        if (!numberPattern.matcher(code).matches()) {
            throw new IllegalArgumentException(Message.VALIDATE_PARSE_TO_NUMBER_ERROR.getMessage());
        }
    }
}
