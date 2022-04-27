package com.prgrms.vouchermanagement.customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerDto {

    private UUID customerId;
    private String email;
    private LocalDateTime createdAt;

    public CustomerDto(UUID customerId, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getCustomerId(), customer.getEmail(), customer.getCreatedAt());
    }

    public static List<CustomerDto> fromList(List<Customer> customers) {
        List<CustomerDto> customerDtos = new ArrayList<>();
        customers.forEach(customer -> {
            customerDtos.add(CustomerDto.from(customer));
        });
        return customerDtos;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
