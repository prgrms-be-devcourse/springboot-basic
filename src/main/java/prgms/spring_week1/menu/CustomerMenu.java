package prgms.spring_week1.menu;

import java.util.stream.Stream;

public enum CustomerMenu {
    INSERT("insert"),
    FIND_ALL("findall"),
    FIND_BY_ID("findbyid"),
    DELETE("delete");

    private final String customerMenuType;

    CustomerMenu(String customerMenuType) {
        this.customerMenuType = customerMenuType;
    }

    public static CustomerMenu findMenuType(String inputText) {
        return Stream.of(values())
                .filter(menu -> menu.name().equalsIgnoreCase(inputText))
                .findFirst()
                .orElse(null);
    }
}
