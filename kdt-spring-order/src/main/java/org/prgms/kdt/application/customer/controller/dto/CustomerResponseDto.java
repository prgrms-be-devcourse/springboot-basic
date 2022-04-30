package org.prgms.kdt.application.customer.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.prgms.kdt.application.customer.domain.Customer;

@Getter @Setter
public class CustomerResponseDto {
    UUID customerId;
    String name;
    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public CustomerResponseDto(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.createdAt = customer.getCreatedAt();
        this.updatedAt = customer.getUpdatedAt();
    }
}
