package org.prgms.voucherProgram.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.voucherProgram.customer.domain.Customer;

public class CustomerDto {
    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime updatedDateTime;

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getCustomerId(), customer.getName(), customer.getEmail(),
            customer.getCreatedDateTime(), customer.getUpdatedDateTime());
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }
}
