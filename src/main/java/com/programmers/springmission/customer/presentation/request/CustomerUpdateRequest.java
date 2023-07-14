package com.programmers.springmission.customer.presentation.request;

import lombok.Getter;

@Getter
public class CustomerUpdateRequest {

    private final String name;

    public CustomerUpdateRequest(String name) {
        this.name = name.trim();
    }
}
