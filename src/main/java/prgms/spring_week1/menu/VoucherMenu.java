package prgms.spring_week1.menu;

import java.util.stream.Stream;

public enum VoucherMenu {
    INSERT("insert"),
    FIND_ALL("findall"),
    FIND_BY_TYPE("findbytype"),
    DELETE("delete");

    private final String customerMenuType;

    VoucherMenu(String customerMenuType) {
        this.customerMenuType = customerMenuType;
    }

    public static VoucherMenu findMenuType(String inputText) {
        return Stream.of(values())
                .filter(menu -> menu.name().equalsIgnoreCase(inputText))
                .findFirst()
                .orElse(null);
    }
}
