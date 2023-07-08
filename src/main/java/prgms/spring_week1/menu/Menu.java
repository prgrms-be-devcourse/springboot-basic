package prgms.spring_week1.menu;

import java.util.stream.Stream;

public enum Menu {
    EXIT,
    CREATE,
    LIST,
    BLACK;

    public static Menu findMenuType(String inputText) {
        return Stream.of(Menu.values())
                .filter(menu -> menu.name().equalsIgnoreCase(inputText))
                .findFirst()
                .orElse(null);
    }
}
