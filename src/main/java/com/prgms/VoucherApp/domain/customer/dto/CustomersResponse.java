package com.prgms.VoucherApp.domain.customer.dto;

import java.util.Collections;
import java.util.List;

public record CustomersResponse(
    List<CustomerResponse> customers
) {
    public List<CustomerResponse> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }
}
