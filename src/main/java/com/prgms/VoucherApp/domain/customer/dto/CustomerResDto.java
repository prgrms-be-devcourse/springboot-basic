package com.prgms.VoucherApp.domain.customer.dto;

import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;

import java.util.UUID;

public class CustomerResDto {

    private UUID customerId;
    private CustomerStatus customerStatus;

    public CustomerResDto(UUID customerId, CustomerStatus customerStatus) {
        this.customerId = customerId;
        this.customerStatus = customerStatus;
    }

    public CustomerResDto(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.customerStatus = customer.getCustomerStatus();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public String getCustomerInfo() {
        return "Customer Id: " + customerId + ", Customer Status: " + customerStatus;
    }
}
