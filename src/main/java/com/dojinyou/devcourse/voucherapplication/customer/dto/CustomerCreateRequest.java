package com.dojinyou.devcourse.voucherapplication.customer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CustomerCreateRequest {
    private String name;

    @JsonCreator
    public CustomerCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
