package org.prgrms.assignment.voucher.model;

import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum Menu {
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list","Type list to list all vouchers.");

    private static final String TOO_LONG_COMMAND_ERROR = "Your COMMAND length is over than 1!";
    private static final String NO_MENU_NAMED_ERROR = "There is no menu named ";
    private static final Map<String, Menu> commandToMenuMap = new HashMap<>();
    static {
        for(Menu menu : Menu.values()) {
            commandToMenuMap.put(menu.menuName, menu);
        }
    }
    private final String menuName;

    private final String menuExplain;

    private static final Menu[] menus = Menu.values();

    Menu(String menuName, String menuExplain) {
        this.menuName = menuName;
        this.menuExplain = menuExplain;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuExplain() {
        return menuExplain;
    }

    public static boolean isValidMenu(String command) {
        for (Menu menu : menus) {
            if (menu.getMenuName().equals(command)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLongerThanOne(String command) {
        String[] parsedCommand = command.
            split(" ");
        return parsedCommand.length >= 2;
    }

    public static Menu of(String command) {
        if(!isValidMenu(command)) {
            throw new IllegalArgumentException(NO_MENU_NAMED_ERROR + command);
        }
        if(isLongerThanOne(command)) {
            throw new IllegalArgumentException(TOO_LONG_COMMAND_ERROR);
        }
        return commandToMenuMap.get(command);
    }
}
