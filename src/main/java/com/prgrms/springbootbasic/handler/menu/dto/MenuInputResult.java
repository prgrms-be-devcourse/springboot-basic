package com.prgrms.springbootbasic.handler.menu.dto;

public class MenuInputResult {
    private final String command;

    public MenuInputResult(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
