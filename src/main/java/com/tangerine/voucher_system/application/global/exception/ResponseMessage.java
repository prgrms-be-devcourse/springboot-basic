package com.tangerine.voucher_system.application.global.exception;

public enum ResponseMessage {
    CREATE_SUCCESS(" is created successfully."),
    UPDATE_SUCCESS(" is updated successfully."),
    DELETE_SUCCESS(" is deleted successfully.");

    private final String prompt;

    ResponseMessage(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }
}
