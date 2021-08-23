package org.prgrms.kdt.devcourse.customer;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String customerName;
    private final Boolean isBlackCustomer;

    public Customer(UUID customerId, String customerName, Boolean isBlackCustomer) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.isBlackCustomer = isBlackCustomer;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Boolean getBlackCustomer() {
        return isBlackCustomer;
    }
}
