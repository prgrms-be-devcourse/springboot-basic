package com.programmers.customer.controller.dto;

public class CreateCustomerRequest {
    String email;
    String password;
    String name;

    public CreateCustomerRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
