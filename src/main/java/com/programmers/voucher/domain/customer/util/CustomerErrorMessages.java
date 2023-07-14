package com.programmers.voucher.domain.customer.util;

public final class CustomerErrorMessages {
    public static final String DUPLICATE_EMAIL = "Duplicate email exist: Email {0}";
    public static final String NO_SUCH_CUSTOMER = "Customer does not exist: CustomerId {0}";
    public static final String INVALID_EMAIL =
            "Username up to 20 characters, including [A-Za-z0-9._%+-]" +
            " and Domain up to 23 characters, including [A-Za-z0-9.-].[A-Za-z]" +
            ": Current Email {0}";
    public static final String INVALID_NAME =
            "Username up to 20 characters, including [A-Za-z0-9]" +
            ": Current Name: {0}";


    private CustomerErrorMessages() {
    }
}
