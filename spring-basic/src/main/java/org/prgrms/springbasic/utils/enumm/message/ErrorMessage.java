package org.prgrms.springbasic.utils.enumm.message;

public enum ErrorMessage {

    PARSING_ERROR("Type the correct number type."),

    COMMAND_ERROR("Please check your command."),

    DISCOUNT_INFO_ERR( "Check the discount information you entered."),

    NOT_EXIST_ENUM_TYPE("Can't find any enum type"),

    NOT_INSERTED("Nothing was inserted."),

    NOT_UPDATED("Nothing was updated.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
