package prgms.spring_week1.menu;

import java.util.stream.Stream;

public enum VoucherMenu {
    INSERT("insert"),
    FIND_ALL("findall"),
    FIND_BY_TYPE("findbytype"),
    DELETE_ALL("deleteall");

    private final String voucherMenuType;

    VoucherMenu(String voucherMenuType) {
        this.voucherMenuType = voucherMenuType;
    }

    public static VoucherMenu findMenuType(String inputText) {
        return Stream.of(values())
                .filter(menu -> menu.voucherMenuType.equalsIgnoreCase(inputText))
                .findFirst()
                .orElse(null);
    }
}
