package com.prgms.VoucherApp.domain.customer.dto;

import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;

import java.util.UUID;

public record CustomerResponse(
    UUID customerId,
    CustomerStatus customerStatus
) {
    public CustomerResponse(Customer customer) {
        this(customer.getCustomerId(), customer.getCustomerStatus());
    }
}
