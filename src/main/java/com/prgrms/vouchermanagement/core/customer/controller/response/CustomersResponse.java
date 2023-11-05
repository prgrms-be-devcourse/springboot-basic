package com.prgrms.vouchermanagement.core.customer.controller.response;

import java.util.List;

public class CustomersResponse {

    private final List<CustomerResponse> customerResponses;

    public CustomersResponse(List<CustomerResponse> customerResponses) {
        this.customerResponses = customerResponses;
    }
}
