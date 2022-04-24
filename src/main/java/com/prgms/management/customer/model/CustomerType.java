package com.prgms.management.customer.model;

public enum CustomerType {
    WHITE, BLACK, NONE;

    public static CustomerType of(String str) {
        switch (str.toUpperCase()) {
            case "WHITE":
                return WHITE;
            case "BLACK":
                return BLACK;
            default:
                return NONE;
        }
    }
}
