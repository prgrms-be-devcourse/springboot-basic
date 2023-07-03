package me.kimihiqq.vouchermanagement.option;

import java.util.Arrays;

public enum MainMenuOption implements ConsoleOption {
    EXIT(0, "Exit the program"),
    MANAGE_VOUCHERS(1, "Manage vouchers"),
    MANAGE_CUSTOMERS(2, "Manage customers");

    private final int key;
    private final String description;

    MainMenuOption(int key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }
}