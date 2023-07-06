package com.programmers.springweekly.dto;

import com.programmers.springweekly.domain.customer.CustomerType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerUpdateDto {

    private final UUID customerId;
    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;

    public CustomerUpdateDto(UUID customerId, String customerName, String customerEmail, CustomerType customerType) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerType = customerType;
    }
}
