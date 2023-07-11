package com.devcourse.springbootbasic.application.global.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.util.Arrays;
import java.util.Objects;

public enum DomainMenu {
    CUSTOMER,
    VOUCHER;

    public static DomainMenu getDomainMenu(String input) {
        return Arrays.stream(DomainMenu.values())
                .filter(domainMenu -> Objects.equals(input, domainMenu.name()) || Objects.equals(input, String.valueOf(domainMenu.ordinal())))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_MENU.getMessageText()));
    }
}
