package org.programmers.springboot.basic.domain.customer.dto;

import lombok.Builder;
import lombok.Getter;
import org.programmers.springboot.basic.domain.customer.entity.CustomerType;

import java.util.UUID;

@Builder
@Getter
public class CustomerResponseDto {

    private final UUID customerId;
    private final String name;
    private final String email;
    private final CustomerType customerType;
}
