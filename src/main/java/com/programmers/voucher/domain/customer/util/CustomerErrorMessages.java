package com.programmers.voucher.domain.customer.util;

public final class CustomerErrorMessages {
    public static final String DUPLICATE_EMAIL = "Duplicate email exist: Email {0}";
    public static final String NO_SUCH_CUSTOMER = "Customer does not exist: CustomerId {0}";
    public static final String INVALID_EMAIL_RANGE = "Emails can be up to 20 characters for the username and up to 20 characters for the domain.";
    public static final String INVALID_NAME_RANGE = "The name must be 20 characters or less.";

    private CustomerErrorMessages() {
    }
}
