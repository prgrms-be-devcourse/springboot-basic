package com.dev.voucherproject.model.menu;

import com.dev.voucherproject.controller.console.MenuController;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String menuName;
    private MenuController controller;

    @Component
    public static class MenuExecutorInitializer {
        private final MenuControllerProvider provider;

        public MenuExecutorInitializer(MenuControllerProvider provider) {
            this.provider = provider;
        }

        @PostConstruct
        public void init() {
            Arrays.stream(Menu.values())
                    .forEach(menu -> {
                        menu.controller = provider.provide(menu.getMenuControllerName());
                    });
        }
    }

    Menu(String menuName) {
        this.menuName = menuName;
    }

    public void execute() {
        controller.execute();
    }

    public static Menu convertInputToMenu(String input) {
        return Arrays.stream(Menu.values())
                .filter(m -> m.isExistMenu(input))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("입력 형식이 올바르지 않습니다."));
    }

    private String getMenuControllerName() {
        return this.menuName + "MenuController";
    }

    private boolean isExistMenu(String input) {
        return this.menuName.equals(input);
    }
}
