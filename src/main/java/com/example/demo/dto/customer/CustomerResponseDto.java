package com.example.demo.dto.customer;

import com.example.demo.domain.customer.Customer;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerResponseDto {

    private final UUID id;
    private final String name;
    private final int age;

    public static CustomerResponseDto from(Customer customer) {
        return new CustomerResponseDto(customer.getCustomerId(), customer.getName(), customer.getAge());
    }
}
