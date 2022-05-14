package com.dojinyou.devcourse.voucherapplication.customer.dto;

public class CustomerCreateRequest {
    private String name;

    public CustomerCreateRequest() {}

    public CustomerCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
