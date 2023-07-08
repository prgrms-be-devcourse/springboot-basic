package com.devcourse.voucher.presentation;

public enum Command {
    CREATE,
    LIST,
    EXIT,
    ;

    public boolean isCreation() {
        return this == CREATE;
    }
}
