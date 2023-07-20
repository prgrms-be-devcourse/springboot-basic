package com.prgms.VoucherApp.domain.customer.dto;

import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;

import java.util.UUID;

public record CustomerUpdateRequest(
        UUID id,
        CustomerStatus customerStatus
) {

    public Customer toEntity() {
        Customer customer = new Customer(id, customerStatus);
        return customer;
    }
}
