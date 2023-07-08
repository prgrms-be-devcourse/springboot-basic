package org.weekly.weekly.customer.dto.response;

import org.weekly.weekly.customer.domain.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {
    String name;
    String email;
    LocalDateTime createAt;

    private CustomerDto(String name, String email, LocalDateTime createAt) {
        this.name = name;
        this.email = email;
        this.createAt = createAt;
    }

    public static CustomerDto of(Customer customer) {
        return new CustomerDto(customer.getName(), customer.getEmail(), customer.getCreateAt());
    }
}
