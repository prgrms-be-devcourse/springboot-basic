package org.prgrms.kdt.voucher.controller;

public enum VoucherMenu {
    CREATE("create"),
    OWNER("owner"),
    FIND("find"),
    DETAILS("details"),
    REMOVE("remove");

    private final String value;

    VoucherMenu(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static VoucherMenu fromString(String text) {
        for (VoucherMenu menu : VoucherMenu.values()) {
            if (menu.value.equalsIgnoreCase(text)) {
                return menu;
            }
        }
        return null; // You may handle null as per your requirement
    }
}
