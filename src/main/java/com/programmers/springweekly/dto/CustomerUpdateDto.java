package com.programmers.springweekly.dto;

import com.programmers.springweekly.domain.customer.CustomerType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CustomerUpdateDto {

    private final UUID customerId;
    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;
}
