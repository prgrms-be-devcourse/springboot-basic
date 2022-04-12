package org.prgrms.weeklymission.utils;

public enum ErrorMessage {
    PARSING_ERROR("Type the correct number type."),
    OPTION_ERROR("Please check your option."),
    NO_VOUCHER("Can't find any voucher."),
    NO_CUSTOMER("Can't find any customer."),
    AFTER_DISCOUNT_PRICE_ERR( """
            Check the discount information you entered.
            (0 <= AfterDiscount < BeforeDiscount).
                            """);


    private final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
