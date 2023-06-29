package com.prgms.springbootbasic.global.util;

import com.prgms.springbootbasic.global.exception.NoSuchMenuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public enum Menu {

    EXIT("exit"),
    MEMBER("member"),
    VOUCHER("voucher");

    private static final List<Menu> MENU_LIST = Arrays.stream(Menu.values()).toList();
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);
    private final String command;

    Menu(String command) {
        this.command = command;
    }

    public static Menu of(String command) {
        return MENU_LIST.stream()
                .filter(m -> m.command.equals(command))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("해당되는 메뉴가 없습니다. 입력 값 : {}", command);
                    return new NoSuchMenuException(ExceptionMessage.NO_SUCH_MENU);
                });
    }

}
