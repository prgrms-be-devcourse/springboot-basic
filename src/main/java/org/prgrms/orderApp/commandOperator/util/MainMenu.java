package org.prgrms.orderApp.commandOperator.util;

import java.util.Arrays;

public enum MainMenu {

    CREATE_VOUCHER(1),
    VOUCHER_LIST(2),
    CREATE_ORDER(3),
    ORDER_LIST(4),
    CUSTOMER_LIST(5),
    CREATE_COLLECTION(6),
    EXIT(7);


    private final int menuNumber;

    MainMenu(int menuNumber){
        this.menuNumber = menuNumber;
    }


    public static String getMenuNameByMenuNumber(int menuNumber){
        var getMenuName = Arrays.stream(MainMenu.values())
                .filter(mainMenu -> mainMenu.menuNumber == menuNumber)
                .findAny();
        return getMenuName.map(Enum::name).orElse("");

    }
}
