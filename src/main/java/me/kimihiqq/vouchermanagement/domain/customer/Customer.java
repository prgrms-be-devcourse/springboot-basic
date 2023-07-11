package me.kimihiqq.vouchermanagement.domain.customer;

import me.kimihiqq.vouchermanagement.option.CustomerStatus;

import java.util.UUID;

public class Customer {
    private UUID id;
    private String name;
    private String email;
    private CustomerStatus customerStatus;

    public Customer(UUID id, String name, String email, CustomerStatus customerStatus) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.customerStatus = customerStatus;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void updateCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    @Override
    public String toString() {
        return id + ": " + name + " - " + email;
    }

}