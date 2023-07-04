package com.dev.voucherproject.controller.console;


import com.dev.voucherproject.model.menu.Menu;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class MenuControllerExecutor {
    private final Map<String, MenuController> menuControllerMap;

    public MenuControllerExecutor(Map<String, MenuController> menuControllerMap) {
        this.menuControllerMap = menuControllerMap;
    }

    public void execute(final Menu menu) {
        MenuController menuController = findMenuController(menu);
        menuController.execute();
    }

    private MenuController findMenuController(Menu menu) {
        return menuControllerMap.get(getMenuControllerName(menu));
    }

    private String getMenuControllerName(Menu menu) {
        return menu.getMenuName() + "MenuController";
    }
}
