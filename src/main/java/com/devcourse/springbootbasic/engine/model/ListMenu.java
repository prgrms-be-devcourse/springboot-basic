package com.devcourse.springbootbasic.engine.model;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;

import java.util.Arrays;

public enum ListMenu {
    VOUCHER_LIST("1"),
    BLACK_CUSTOMER_LIST("2");

    private String listMenuOrdinal;

    ListMenu(String listMenuOrdinal) {
        this.listMenuOrdinal = listMenuOrdinal;
    }

    public static ListMenu getListMenu(String input) {
        return Arrays.stream(ListMenu.values())
                .filter(l -> l.listMenuOrdinal.equals(input))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(InvalidDataException.INVALID_LIST_MENU));
    }
}
