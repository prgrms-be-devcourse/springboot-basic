package com.programmers.springbootbasic.domain.customer.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerRequestDto {
    private final String email;
    private final String name;
}
