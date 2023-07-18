package com.example.demo.domain.customer;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@Builder
@ToString
public class Customer {

    private final UUID customerId;
    private final String name;
    private final int age;
}
