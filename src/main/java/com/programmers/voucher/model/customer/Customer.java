package com.programmers.voucher.model.customer;

public class Customer {
    private final long customerId;
    private final String customerName;
    private final String email;

    public Customer(long customerId, String customerName, String email) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }
}
