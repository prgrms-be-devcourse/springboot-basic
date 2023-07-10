package com.programmers.springweekly.dto.customer.request;

import com.programmers.springweekly.domain.customer.CustomerType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerUpdateRequest {

    private UUID customerId;
    private String customerName;
    private String customerEmail;
    private CustomerType customerType;

    @Builder
    public CustomerUpdateRequest(UUID customerId, String customerName, String customerEmail, CustomerType customerType) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerType = customerType;
    }
    
}
