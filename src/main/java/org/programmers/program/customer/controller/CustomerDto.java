package org.programmers.program.customer.controller;

import org.programmers.program.customer.model.Customer;

import java.util.UUID;

public class CustomerDto {
    private UUID id;
    private String email;
    private String name;

    public CustomerDto(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Customer to(){
        return new Customer(id, email, name);
    }
}
