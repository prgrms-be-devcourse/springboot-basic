package com.programmers.voucher.domain.customer.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CustomerCreateRequest {
    private static final String EMAIL_NOT_NULL = "Email must not be null";
    private static final String EMAIL_IN_RANGE = "Email username 20 chars or less and domain name 20 chars or less";
    private static final String NAME_NOT_NULL = "Name must not be null";
    private static final String NAME_IN_RANGE = "Name 20 chars or less";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]{1,20}@[A-Za-z0-9.-]{1,20}\\.[A-Za-z]{2,3}$";
    private static final String NAME_PATTERN = "^[A-Za-z0-9]{1,20}";

    @NotNull(message = EMAIL_NOT_NULL)
    @Pattern(regexp = EMAIL_PATTERN, message = EMAIL_IN_RANGE)
    private final String email;
    @NotNull(message = NAME_NOT_NULL)
    @Pattern(regexp = NAME_PATTERN, message = NAME_IN_RANGE)
    private final String name;

    public CustomerCreateRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
