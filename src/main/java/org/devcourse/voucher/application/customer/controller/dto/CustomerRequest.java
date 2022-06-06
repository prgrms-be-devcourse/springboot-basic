package org.devcourse.voucher.application.customer.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.devcourse.voucher.core.exception.ErrorType.*;

public class CustomerRequest {

    private final String name;

    private final String email;

    public CustomerRequest(@JsonProperty("name") String name, @JsonProperty("email") String email) {
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
