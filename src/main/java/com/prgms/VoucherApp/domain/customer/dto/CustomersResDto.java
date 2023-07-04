package com.prgms.VoucherApp.domain.customer.dto;

import java.util.Collections;
import java.util.List;

public class CustomersResDto {

    private final List<CustomerResDto> customers;

    public CustomersResDto(List<CustomerResDto> customers) {
        this.customers = customers;
    }

    public List<CustomerResDto> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }
}
