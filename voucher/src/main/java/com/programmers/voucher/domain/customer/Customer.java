package com.programmers.voucher.domain.customer;


import java.util.Map;
import java.util.UUID;

public class Customer {
    private String customerId;
    private String name;
    private String email;


    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public static Customer createCustomer(Map<String, String> customerInformation) {
        return new Customer(
                UUID.randomUUID().toString().substring(0, 7),
                customerInformation.get("name"),
                customerInformation.get("email"));
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
