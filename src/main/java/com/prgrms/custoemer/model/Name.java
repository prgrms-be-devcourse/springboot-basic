package com.prgrms.custoemer.model;

import com.prgrms.presentation.message.ErrorMessage;

public class Name {

    private String value;

    public Name(String value) {
        validateName(value);
        this.value = value;
    }

    public void changeName(String value) {
        validateName(value);
        this.value = value;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.NULL_ARGUMENT.getMessage());
        }
    }

    public String getValue() {
        return value;
    }

}
