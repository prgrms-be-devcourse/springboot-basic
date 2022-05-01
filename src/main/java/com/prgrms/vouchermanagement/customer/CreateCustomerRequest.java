package com.prgrms.vouchermanagement.customer;

public class CreateCustomerRequest {

    private String name;
    private String email;

    public CreateCustomerRequest(String name, String email) {
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
