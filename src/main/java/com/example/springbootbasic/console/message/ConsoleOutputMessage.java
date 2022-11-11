package com.example.springbootbasic.console.message;

public enum ConsoleOutputMessage {
    SAVE_VOUCHER_SUCCESS("바우처 저장에 성공하였습니다.");

    private final String message;

    ConsoleOutputMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
