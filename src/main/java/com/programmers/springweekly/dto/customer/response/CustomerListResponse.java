package com.programmers.springweekly.dto.customer.response;

import java.util.List;
import lombok.Getter;

@Getter
public class CustomerListResponse {

    private final List<CustomerResponse> customerList;

    public CustomerListResponse(List<CustomerResponse> customerList) {
        this.customerList = customerList;
    }

}
