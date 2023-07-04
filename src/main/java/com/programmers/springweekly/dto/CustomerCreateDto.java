package com.programmers.springweekly.dto;

import com.programmers.springweekly.domain.customer.CustomerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerCreateDto {

    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;
}
