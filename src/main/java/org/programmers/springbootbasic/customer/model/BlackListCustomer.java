package org.programmers.springbootbasic.customer.model;

import java.util.UUID;

public class BlackListCustomer implements Customer{
    private final UUID customerId;
    private final String customerName;

    public BlackListCustomer(UUID customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "BlackListCustomer" +
                " customerId= " + customerId +
                " customerName= " + customerName;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }
}
