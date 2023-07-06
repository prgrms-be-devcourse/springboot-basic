package com.programmers.springweekly.dto;

import com.programmers.springweekly.domain.customer.CustomerType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerCreateDto {

    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;
}
