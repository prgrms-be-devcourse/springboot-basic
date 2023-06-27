package me.kimihiqq.vouchermanagement.domain.customer;

import me.kimihiqq.vouchermanagement.option.CustomerStatus;

public class Customer {
    private String id;
    private String name;
    private CustomerStatus status;

    public Customer(String id, String name, CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomerStatus getStatus() {
        return status;
    }
}