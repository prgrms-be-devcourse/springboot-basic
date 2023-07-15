package prgms.spring_week1.menu;

import java.util.stream.Stream;

public enum CustomerMenu {
    INSERT("insert"),
    FIND_ALL("findall"),
    FIND_BY_EMAIL("findbyemail"),
    BLACK("black"),
    UPDATE_INFO("update"),
    DELETE_BY_EMAIL("deletebyemail"),
    DELETE_ALL("deleteall");

    private final String customerMenuType;

    CustomerMenu(String customerMenuType) {
        this.customerMenuType = customerMenuType;
    }

    public static CustomerMenu findMenuType(String inputText) {
        return Stream.of(values())
                .filter(menu -> menu.customerMenuType.equalsIgnoreCase(inputText))
                .findFirst()
                .orElse(null);
    }
}
