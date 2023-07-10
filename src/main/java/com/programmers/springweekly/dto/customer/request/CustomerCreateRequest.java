package com.programmers.springweekly.dto.customer.request;

import com.programmers.springweekly.domain.customer.CustomerType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerCreateRequest {

    private String customerName;
    private String customerEmail;
    private CustomerType customerType;

    @Builder
    public CustomerCreateRequest(String customerName, String customerEmail, CustomerType customerType) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerType = customerType;
    }
    
}
