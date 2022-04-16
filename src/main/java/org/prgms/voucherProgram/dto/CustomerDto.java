package org.prgms.voucherProgram.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.Customer;

public class CustomerDto {
    private final UUID customerId;
    private final LocalDateTime createdTime;
    private final LocalDateTime lastLoginTime;
    private String name;
    private String email;

    public CustomerDto(UUID customerId, String name, String email) {
        this(customerId, name, email, LocalDateTime.now(), null);
    }

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime createdTime,
        LocalDateTime lastLoginTime) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdTime = createdTime;
        this.lastLoginTime = lastLoginTime;
    }

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getCustomerId(), customer.getName(), customer.getEmail(),
            customer.getCreatedTime(),
            customer.getLastLoginTime());
    }

    public Customer toEntity() {
        return new Customer(customerId, name, email, createdTime, lastLoginTime);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "customerId=%s, name=%s, email=%s, createdTime=%s, lastLoginTime=%s".formatted(customerId, name,
            email, createdTime, lastLoginTime);
    }
}
