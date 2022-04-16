package kdt.vouchermanagement;

import kdt.vouchermanagement.exception.InvalidMenuException;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

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
        Optional<Menu> foundMenu = Arrays.stream(values())
                .filter(o -> Objects.equals(o.menu, input))
                .findFirst();
        return foundMenu.orElseThrow(() -> new InvalidMenuException("입력한 메뉴값이 유효하지 않습니다."));
    }
}
