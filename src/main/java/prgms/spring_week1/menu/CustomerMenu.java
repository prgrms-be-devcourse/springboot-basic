package prgms.spring_week1.menu;

import java.util.stream.Stream;

public enum CustomerMenu {
    INSERT,
    FIND_ALL,
    FIND_BY_EMAIL,
    BLACK,
    UPDATE_INFO,
    DELETE_BY_EMAIL,
    DELETE_ALL
    ;

    public static CustomerMenu findMenuType(String inputText) {
        return Stream.of(values())
                .filter(menu -> menu.name().equalsIgnoreCase(inputText))
                .findFirst()
                .orElse(null);
    }
}
