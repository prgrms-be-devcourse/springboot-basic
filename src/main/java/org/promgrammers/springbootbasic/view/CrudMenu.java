package org.promgrammers.springbootbasic.view;

import java.util.Arrays;

public enum CrudMenu {

    CREATE("1"),
    FIND_ALL("2"),
    FIND_ONE("3"),
    DELETE("4"),
    UPDATE("5");
    private final String menuNumber;

    CrudMenu(String menuNumber) {
        this.menuNumber = menuNumber;
    }

    public static CrudMenu from(String inputCrudMenu) {
        return Arrays.stream(values())
                .filter(value -> value.menuNumber.equals(inputCrudMenu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어 입니다. => " + inputCrudMenu));
    }
}
