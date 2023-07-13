package com.programmers.springweekly.dto.customer.response;

import lombok.Getter;

import java.util.List;

@Getter
public class CustomerListResponse {

    private final List<CustomerResponse> customerList;

    public CustomerListResponse(List<CustomerResponse> customerList) {
        this.customerList = customerList;
    }
}
