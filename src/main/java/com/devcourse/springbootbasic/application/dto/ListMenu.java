package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.constant.Message;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum ListMenu {
    VOUCHER_LIST("1", "Created Vouchers List"),
    BLACK_CUSTOMER_LIST("2", "Black Customers List");

    private static final Logger logger = LoggerFactory.getLogger(ListMenu.class);

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
                    logger.error("Menu Error - {} : {}", input, Message.INVALID_LIST_MENU);
                    return new InvalidDataException(Message.INVALID_LIST_MENU.getMessageText());
                });
    }

    public String getListMenuOrdinal() {
        return listMenuOrdinal;
    }

    public String getListMenuPrompt() {
        return listMenuPrompt;
    }
}
