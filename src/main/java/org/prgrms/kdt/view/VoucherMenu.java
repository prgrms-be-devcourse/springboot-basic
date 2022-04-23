package org.prgrms.kdt.view;

public enum VoucherMenu {
    FIXED("ex_1) fixed 1000"),
    PERCENT("ex_2) percent 15");

    private final String message;

    VoucherMenu(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
