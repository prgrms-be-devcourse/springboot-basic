package prgms.spring_week1.Menu;

import java.util.stream.Stream;

public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String menuName;

    Menu(String menuName) {
        this.menuName = menuName;
    }

    public static Menu findMenuType(String inputText){
        return Stream.of(Menu.values())
                .filter(menu -> menu.menuName.equalsIgnoreCase(inputText))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid input"));
    }
}
