package prgms.vouchermanagementapp.io;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MenuType {
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list", "Type list to list all vouchers.");

    private final String menu;
    private final String message;

    MenuType(String menu, String message) {
        this.menu = menu;
        this.message = message;
    }

    public static List<String> getMessages() {
        return Arrays.stream(MenuType.values())
                .map(menuType -> menuType.message)
                .collect(Collectors.toList());
    }

    public static MenuType of(String menu) {
        return Arrays.stream(MenuType.values())
                .filter(menuType -> menuType.menu.equals(menu))
                .findFirst()
                .orElseThrow(NoSuchFieldError::new);
    }

    public boolean isExit() {
        return this == EXIT;
    }

    public boolean isCreate() {
        return this == CREATE;
    }

    public boolean isList() {
        return this == LIST;
    }
}
