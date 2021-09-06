package org.prgrms.orderApp.commandOperator.util;

import java.util.Arrays;

public enum MonguMainMenu {
    COLLECTION_CREATE(1),
    COLLECTION_DROP(2);



    private final int menuNumber;

    MonguMainMenu(int menuNumber){
        this.menuNumber = menuNumber;
    }


    public static String getMenuNameByMenuNumber(int menuNumber){

        var getMonguMainMenu = Arrays.stream(MonguMainMenu.values())
                .filter(monguMainMenu -> monguMainMenu.menuNumber == menuNumber)
                .findAny();
        return getMonguMainMenu.map(Enum::name).orElse("");
    }
}
