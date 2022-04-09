package com.mountain.voucherApp.utils;

import com.mountain.voucherApp.enums.Menu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MenuUtil {

    private static Map<String ,Menu> map = null;

    public static boolean isExit(String command) {
        return command.equals(Menu.EXIT.getValue());
    }

    public static boolean isCreate(String command) {
        return command.equals(Menu.CREATE.getValue());
    }

    public static boolean isList(String command) {
        return command.equals(Menu.LIST.getValue());
    }

    public static Map<String, Menu> getMenuMap() {
        if (map == null) {
            map = new HashMap<>();
            Arrays.stream(Menu.values())
                    .forEach((menu) -> {
                        map.put(menu.getValue(), menu);
                    });
        }
        return map;
    }
}
