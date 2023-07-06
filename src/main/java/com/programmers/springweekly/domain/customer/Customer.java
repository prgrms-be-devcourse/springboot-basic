package com.programmers.springweekly.domain.customer;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Customer {

    private final UUID customerId;
    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;
}
