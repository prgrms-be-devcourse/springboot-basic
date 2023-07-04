package com.programmers.domain.customer.dto;

import com.programmers.domain.customer.CustomerType;

public record CustomerCreateRequestDto(String name, CustomerType type) {

}
