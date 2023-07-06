package com.programmers.springweekly.dto.customer.request;

import com.programmers.springweekly.domain.customer.CustomerType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerUpdateRequest {

    private final UUID customerId;
    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;

    @Builder
    public CustomerUpdateRequest(UUID customerId, String customerName, String customerEmail, CustomerType customerType) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerType = customerType;
    }
}
