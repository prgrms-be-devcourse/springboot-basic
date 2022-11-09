package com.example.springbootbasic.domain.customer;

import java.text.MessageFormat;

import static com.example.springbootbasic.util.CharacterUnit.ENTER;

public enum CustomerMessage {
    MENU(MessageFormat.format(
            "=== Customer Program ==={0}" +
                    "Type **customer-black-list** to exit the program. {1}",
            ENTER.getUnit(), ENTER.getUnit()));

    private final String message;

    CustomerMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
