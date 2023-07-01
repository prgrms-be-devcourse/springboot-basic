package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.constant.ErrorMessage;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum ListMenu {
    VOUCHER_LIST("1", "Created Vouchers List"),
    BLACK_CUSTOMER_LIST("2", "Black Customers List");

    private final String listMenuOrdinal;
    private final String listMenuPrompt;

    ListMenu(String listMenuOrdinal, String listMenuPrompt) {
        this.listMenuOrdinal = listMenuOrdinal;
        this.listMenuPrompt = listMenuPrompt;
    }

    public static ListMenu getListMenu(String input) {
        return Arrays.stream(ListMenu.values())
                .filter(l -> l.listMenuOrdinal.equals(input))
                .findAny()
                .orElseThrow(() -> {
                    return new InvalidDataException(ErrorMessage.INVALID_LIST_MENU.getMessageText());
                });
    }

    public String getListMenuOrdinal() {
        return listMenuOrdinal;
    }

    public String getListMenuPrompt() {
        return listMenuPrompt;
    }
}
