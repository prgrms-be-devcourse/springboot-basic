package org.prgrms.kdtspringdemo.domain.console;

import java.util.Arrays;

public enum Menu {
    EXIT("The Program is terminated"),
    CREATE("Choose one of voucher type. it will be created"),
    LIST("Show all voucher list"),
    CUSTOMER("Register customer or see customer list"),
    VOUCHER("Manage voucher"),
    MAPPING("Mapping customer to voucher"),
    BLACKLIST("Add person to blacklist"),
    None("Type letter correctly");

    private final String stateInfo;

    Menu(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static Menu of(String inputMenu) {
        return Arrays.stream(Menu.values())
                .filter(menu -> String.valueOf(menu).equalsIgnoreCase(inputMenu))
                .findFirst()
                .orElse(None);
    }

    public void writeStateInfo() {
        System.out.println(stateInfo);
    }

}
