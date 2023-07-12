package com.example.demo.dto;

import com.example.demo.domain.customer.Customer;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerDto {

    private final UUID id;
    private final String name;
    private final int age;

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getCustomerId(), customer.getName(), customer.getAge());
    }

}
