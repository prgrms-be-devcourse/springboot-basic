package com.programmers.springmission.customer.presentation.response;

import com.programmers.springmission.customer.domain.Customer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CustomerResponse {

    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    public CustomerResponse(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.createdAt = customer.getCreatedAt();
    }

    @Override
    public String toString() {
        return "Customer {" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
