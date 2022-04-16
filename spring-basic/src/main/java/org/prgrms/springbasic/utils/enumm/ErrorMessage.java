package org.prgrms.springbasic.utils.enumm;

public enum ErrorMessage {

    PARSING_ERROR("Type the correct number type."),
    COMMAND_ERROR("Please check your command."),
    NO_VOUCHER("Can't find any voucher."),
    NO_CUSTOMER("Can't find any customer."),
    AFTER_DISCOUNT_PRICE_ERR( """
            Check the discount information you entered.
            (0 <= AfterDiscount < BeforeDiscount).
                            """),
    NOT_EXIST_ENUM_TYPE("Can't find any enum type");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
