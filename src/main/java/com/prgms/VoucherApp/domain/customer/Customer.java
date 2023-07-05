package com.prgms.VoucherApp.domain.customer;

import java.util.UUID;

public class Customer {

    private UUID customerId;
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
}
