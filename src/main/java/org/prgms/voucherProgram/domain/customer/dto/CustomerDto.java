package org.prgms.voucherProgram.domain.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.domain.Customer;

public class CustomerDto {
    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime createdTime;
    private final LocalDateTime updatedTime;

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime createdTime,
        LocalDateTime updatedTime) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getCustomerId(), customer.getName(), customer.getEmail(),
            customer.getCreatedTime(), customer.getUpdatedTime());
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}
