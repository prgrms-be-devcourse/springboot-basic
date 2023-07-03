package com.programmers.springweekly.domain.customer;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Customer {

    private final UUID customerId;
    private final CustomerType customerType;
    
}
