package com.programmers.springweekly.dto;

import com.programmers.springweekly.domain.customer.CustomerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CustomerUpdateDto {

    private final UUID customerId;
    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;
}
