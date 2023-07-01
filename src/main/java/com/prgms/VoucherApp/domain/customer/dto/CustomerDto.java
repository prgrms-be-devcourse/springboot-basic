package com.prgms.VoucherApp.domain.customer.dto;

import com.prgms.VoucherApp.domain.customer.Customer;
import com.prgms.VoucherApp.domain.customer.CustomerStatus;

import java.util.UUID;

public class CustomerDto {

    private UUID customerId;
    private CustomerStatus customerStatus;

    public CustomerDto(String customerId, CustomerStatus customerStatus) {
        this.customerId = UUID.fromString(customerId);
        this.customerStatus = customerStatus;
    }

    public CustomerDto(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.customerStatus = customer.getCustomerStatus();
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public CustomerStatus getCustomerStatus() {
        return this.customerStatus;
    }

    public String getCustomerInfo() {
        return "Customer Id: " + customerId + ", Customer Status: " + customerStatus;
    }
}
