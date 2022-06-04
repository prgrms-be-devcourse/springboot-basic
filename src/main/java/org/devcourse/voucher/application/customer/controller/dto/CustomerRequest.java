package org.devcourse.voucher.application.customer.controller.dto;

import static org.devcourse.voucher.core.exception.ErrorType.*;

public class CustomerRequest {

    private final String name;

    private final String email;

    public CustomerRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }



}
