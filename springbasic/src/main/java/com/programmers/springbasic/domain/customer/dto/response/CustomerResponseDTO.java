package com.programmers.springbasic.domain.customer.dto.response;

import com.programmers.springbasic.domain.customer.entity.Customer;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerResponseDTO {
    private UUID customerId;
    private String name;
    private String email;

    public CustomerResponseDTO(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
        this.email = customer.getEmail();
    }
}
