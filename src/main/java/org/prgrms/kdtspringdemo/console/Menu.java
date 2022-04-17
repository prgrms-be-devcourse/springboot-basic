package org.prgrms.kdtspringdemo.console;

import java.util.Arrays;

public enum Menu {
    EXIT("The Program is terminated"),
    CREATE("Choose one of voucher type. it will be created"),
    LIST("Show all voucher list"),
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
