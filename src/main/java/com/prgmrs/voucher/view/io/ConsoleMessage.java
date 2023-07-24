package com.prgmrs.voucher.view.io;

public enum ConsoleMessage {
    REQUEST_USERNAME("Type username to proceed"),
    REQUEST_DISCOUNT_VALUE("Type discount value which will be either your amount or percentage."),
    REQUEST_VOUCHER_ID("Type voucher id to proceed");

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
