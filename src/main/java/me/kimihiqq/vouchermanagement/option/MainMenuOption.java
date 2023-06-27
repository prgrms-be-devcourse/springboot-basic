package me.kimihiqq.vouchermanagement.option;

import java.util.Arrays;

public enum MainMenuOption implements ConsoleOption {
    EXIT(0, "Exit the program"),
    CREATE(1, "Create a new voucher"),
    LIST(2, "List all vouchers"),
    BLACKLIST(3,"List blacklist");

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
