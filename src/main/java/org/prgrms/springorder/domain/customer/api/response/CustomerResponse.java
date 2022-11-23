package org.prgrms.springorder.domain.customer.api.response;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.springorder.domain.customer.model.CustomerStatus;

public class CustomerResponse {

    private final UUID customerId;

    private final String name;

    private final String email;

    private final LocalDateTime lastLoginAt;

    private final LocalDateTime createdAt;

    private final CustomerStatus customerStatus;

    public CustomerResponse(UUID customerId, String name, String email,
        LocalDateTime lastLoginAt, LocalDateTime createdAt,
        CustomerStatus customerStatus) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.customerStatus = customerStatus;
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

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }
}
