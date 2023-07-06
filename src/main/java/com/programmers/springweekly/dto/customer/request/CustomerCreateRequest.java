package com.programmers.springweekly.dto.customer.request;

import com.programmers.springweekly.domain.customer.CustomerType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerCreateRequest {

    private final String customerName;
    private final String customerEmail;
    private final CustomerType customerType;
}
