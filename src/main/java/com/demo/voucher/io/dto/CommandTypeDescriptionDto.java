package com.demo.voucher.io.dto;

public class CommandTypeDescriptionDto {
    private final String command;
    private final String description;

    public CommandTypeDescriptionDto(String command, String description) {
        this.command = command;
        this.description = description;
    }

    @Override
    public String toString() {
        return command + ": " + description;
    }
}
