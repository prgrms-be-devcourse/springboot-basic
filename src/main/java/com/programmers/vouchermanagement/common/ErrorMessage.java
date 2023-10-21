package com.programmers.vouchermanagement.common;

public enum ErrorMessage {
    INVALID_COMMAND_MESSAGE("[System] Invalid command."),
    FILE_NOT_FOUND_MESSAGE("[System] File not found.");
    
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
