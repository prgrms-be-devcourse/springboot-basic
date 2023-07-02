package com.prgms.voucher.voucherproject.domain;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public enum MenuGroup {
    EXIT("exit", Arrays.asList(MenuType.EXIT)),
    CREATE("create", Arrays.asList(MenuType.CREATE)),
    LIST("list", Arrays.asList(MenuType.LIST));

    private String menuName;
    private List<MenuType> menuTypeList;

    MenuGroup(String menuName, List<MenuType> menuTypeList) {
        this.menuName = menuName;
        this.menuTypeList = menuTypeList;
    }

    public static MenuGroup findByMenuType(MenuType menuType) {
        return Arrays.stream(MenuGroup.values())
                .filter(menuGroup -> menuGroup.hasMenuCode(menuType))
                .findAny()
                .orElseThrow(() -> new InputMismatchException("잘못된 명령어 입니다."));
    }

    private boolean hasMenuCode(MenuType menuType) {
        return menuTypeList.stream()
                .anyMatch(menu -> menu == menuType);
    }
}
