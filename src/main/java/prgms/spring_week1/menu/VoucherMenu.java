package prgms.spring_week1.menu;

import java.util.stream.Stream;

public enum VoucherMenu {
    INSERT,
    FIND_ALL,
    FIND_BY_TYPE,
    DELETE_ALL
    ;

    public static VoucherMenu findMenuType(String inputText) {
        return Stream.of(values())
                .filter(menu -> menu.name().equalsIgnoreCase(inputText))
                .findFirst()
                .orElse(null);
    }
}
