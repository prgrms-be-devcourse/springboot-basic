package kdt.vouchermanagement.global.view;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {
    EXIT_PROGRAM("exit"),
    CREATE_VOUCHER("create"),
    LIST_VOUCHERS("list"),
    BLACKLIST("black");

    private String menu;

    Menu(String menu) {
        this.menu = menu;
    }

    public static Menu from(String input) {
        return Arrays.stream(values())
                .filter(o -> Objects.equals(o.menu, input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("입력한 메뉴값이 유효하지 않습니다."));
    }
}
