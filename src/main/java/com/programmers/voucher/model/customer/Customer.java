package com.programmers.voucher.model.customer;

public class Customer {
    private final String customerId;
    private final String customerName;

    public Customer(String customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "id :" + customerId + ", name :" + customerName;
    }
}
