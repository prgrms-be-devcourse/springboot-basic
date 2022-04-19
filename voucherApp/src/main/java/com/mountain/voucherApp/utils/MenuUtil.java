package com.mountain.voucherApp.utils;

import com.mountain.voucherApp.enums.Menu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MenuUtil {

    private static final Map<Integer ,Menu> map = new ConcurrentHashMap<>();

    private MenuUtil() {}

    public static boolean isExit(int ordinal) {
        Menu menu = map.getOrDefault(ordinal, null);
        if (menu != null)
            return (menu == Menu.EXIT);
        return false;
    }

    public static Map<Integer, Menu> getMenuMap() {
        if (map.size() == 0) {
            Arrays.stream(Menu.values())
                    .forEach((menu) -> {
                        map.put(menu.ordinal(), menu);
                    });
        }
        return map;
    }
}
