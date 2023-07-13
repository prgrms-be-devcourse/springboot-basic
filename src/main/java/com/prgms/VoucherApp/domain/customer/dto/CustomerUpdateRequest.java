package com.prgms.VoucherApp.domain.customer.dto;

import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;

import java.util.UUID;

public record CustomerUpdateRequest(
        UUID id,
        CustomerStatus status
) {

    public Customer toEntity() {
        Customer customer = new Customer(id, status);
        return customer;
    }
}
