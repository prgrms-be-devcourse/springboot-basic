package com.programmers.springmission.customer.presentation.request;

import lombok.Getter;

@Getter
public class CustomerCreateRequest {

    private final String name;
    private final String email;

    public CustomerCreateRequest(String name, String email) {
        this.name = name.trim();
        this.email = email.trim();
    }
}
