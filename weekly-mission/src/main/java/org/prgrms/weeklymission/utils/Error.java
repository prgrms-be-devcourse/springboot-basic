package org.prgrms.weeklymission.utils;

public class Error {
    private Error() {}

    public static final String PARSING_ERROR = "Type the correct number type.";
    public static final String OPTION_ERROR = "Please check your option.";
    public static final String NO_VOUCHER = "Can't find any voucher.";
    public static final String NO_CUSTOMER = "Can't find any customer.";
    public static final String AFTER_DISCOUNT_PRICE_ERR = """
            Check the discount information you entered.
            (0 <= AfterDiscount < BeforeDiscount).
                            """;
}
