package org.devcourse.voucher.model;

import org.devcourse.voucher.voucher.model.VoucherType;

public enum ListType {
    VOUCHER("1"),
    BLACKLIST("2"),
    NONE("");

    private String option;

    ListType(String option) {
        this.option = option;
    }

    public static ListType discriminate(String input) {
        ListType list;

        switch (input) {
            case "1" -> list = VOUCHER;
            case "2" -> list = BLACKLIST;
            default -> list = NONE;
        }
        return list;
    }
}
