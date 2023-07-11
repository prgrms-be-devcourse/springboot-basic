package com.devcourse.springbootbasic.application.global.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.util.Arrays;
import java.util.Objects;

public enum CommandMenu {
    EXIT("Input 0 or EXIT to exit the program"),
    CREATE("Input 1 or create to do Create Task"),
    UPDATE("Input 2 or update to do Update Task"),
    REMOVE("Input 3 or remove to do Remove Task"),
    LIST("Input 4 or list to do List Task");

    private final String menuPrompt;

    CommandMenu(String menuPrompt) {
        this.menuPrompt = menuPrompt;
    }

    public static CommandMenu getCommandMenu(String menuString) {
        return Arrays.stream(CommandMenu.values())
                .filter(menu -> Objects.equals(menuString, menu.name()) || Objects.equals(menuString, String.valueOf(menu.ordinal())))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_MENU.getMessageText()));
    }

    public String getMenuPrompt() {
        return menuPrompt;
    }

}
