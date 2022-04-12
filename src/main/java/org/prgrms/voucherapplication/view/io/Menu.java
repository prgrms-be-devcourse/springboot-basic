package org.prgrms.voucherapplication.view.io;

import org.prgrms.voucherapplication.exception.InvalidMenuException;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Optional;

/**
 * 메뉴 옵션을 정의한 enum class
 */
public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String input;

    Menu(String input) {
        this.input = input;
    }

    /**
     * input이 "exit"이면 EXIT 메뉴
     * input이 "create"이면 CREATE 메뉴
     * input이 "list"이면 LIST 메뉴
     * input이 그 이외의 문자이면 null
     *
     * @param input: 사용자의 입력
     * @return 선택된 메뉴
     */
    public static Menu getMenu(String input) throws InvalidMenuException {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.input.equals(input))
                .findAny()
                .orElseThrow(InvalidMenuException::new);
    }
}
