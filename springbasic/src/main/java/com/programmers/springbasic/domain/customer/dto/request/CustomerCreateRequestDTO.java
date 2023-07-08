package com.programmers.springbasic.domain.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerCreateRequestDTO {
    private String name;
    private String email;
}
