package org.programmers.springorder.customer.model;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final CustomerType customerType;
    private final int age;
    private final String email;

    public Customer(UUID customerId, String name, CustomerType customerType, int age, String email) {
        this.customerId = customerId;
        this.name = name;
        this.customerType = customerType;
        this.age = age;
        this.email = email;
    }

    public boolean isBlackList(){
        if(this.customerType == CustomerType.BLACK) {
            return true;
        }
        return false;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

}
