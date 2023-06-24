package org.weekly.weekly.util;

public enum PrintMsg {
    PROGRAM("=== Voucher Program ==="),
    EMPTY("");

    private String msg;

    PrintMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
