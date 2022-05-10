package com.prgrms.voucher_manager.customer.controller;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.SimpleCustomer;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.prgrms.voucher_manager.infra.Utils.createModelMapper;

public class CustomerDto {

    private UUID customerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;

    private static final ModelMapper modelMapper = createModelMapper();


    public CustomerDto() {}

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public CustomerDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static CustomerDto of(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
