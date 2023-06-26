package com.dev.voucherproject.model.menu;


import com.dev.voucherproject.controller.console.MenuController;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MenuControllerProvider {
    private final Map<String, MenuController> map;

    public MenuControllerProvider(Map<String, MenuController> map) {
        this.map = map;
    }

    public MenuController provide(String controllerName) {
        return map.get(controllerName);
    }
}
