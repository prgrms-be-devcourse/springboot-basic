package com.dojinyou.devcourse.voucherapplication.customer.dto;

public class CustomerUpdateRequest {
    private final long id;
    private final String name;


    public CustomerUpdateRequest(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
