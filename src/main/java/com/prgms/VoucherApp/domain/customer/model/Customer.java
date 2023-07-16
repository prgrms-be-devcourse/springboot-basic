package com.prgms.VoucherApp.domain.customer.model;

import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private CustomerStatus customerStatus;

    public Customer(UUID customerId, CustomerStatus customerStatus) {
        this.customerId = customerId;
        this.customerStatus = customerStatus;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "customerId=" + customerId +
            ", customerStatus=" + customerStatus +
            '}';
    }
}
