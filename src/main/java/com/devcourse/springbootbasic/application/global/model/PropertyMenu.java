package com.devcourse.springbootbasic.application.global.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.util.Arrays;
import java.util.Objects;

public enum PropertyMenu {
    ALL,
    ID,
    BLACK_CUSTOMER;

    public static PropertyMenu getPropertyMenu(String input) {
        return Arrays.stream(PropertyMenu.values())
                .filter(propertyMenu -> Objects.equals(input, propertyMenu.name()) || Objects.equals(input, String.valueOf(propertyMenu.ordinal())))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_MENU.getMessageText()));
    }
}
