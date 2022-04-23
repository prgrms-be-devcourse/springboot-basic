package org.prgrms.voucherapplication.view.io;

import org.prgrms.voucherapplication.exception.InvalidMenuException;

import java.util.Arrays;

/**
 * 메뉴 옵션을 정의한 enum class
 */
public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist");

    private final String input;

    Menu(String input) {
        this.input = input;
    }
}
