package com.programmers.vouchermanagement.consoleapp.menu;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum CreateVoucherMenu {
    FIXED("fixed"),
    PERCENT("percent");

    private final String menuName;

    CreateVoucherMenu(String menuName) {
        this.menuName = menuName;
    }

    //set static to tell that this method does not depend on a particular Menu value
    public static Optional<CreateVoucherMenu> findCreateMenu(String input) {
        return Arrays.stream(CreateVoucherMenu.values())
                .filter(menu -> menu.isMatching(input))
                .findFirst();
    }

    private boolean isMatching(String input) {
        return Objects.equals(menuName, input);
    }

}
