package com.example.voucher_manager.domain.customer;

import java.util.UUID;

public class CustomerDto {
    private final UUID customerId;
    private final String name;
    private final String email;

    public CustomerDto(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getCustomerId(), customer.getName(), customer.getEmail());
    }

    public static Customer toEntity(CustomerDto customerDto) {
        return new Customer(customerDto.getCustomerId(), customerDto.getName(), customerDto.getEmail());
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
