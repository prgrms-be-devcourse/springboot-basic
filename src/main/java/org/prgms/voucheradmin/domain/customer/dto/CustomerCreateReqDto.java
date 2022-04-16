package org.prgms.voucheradmin.domain.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.voucheradmin.domain.customer.entity.Customer;

public class CustomerCreateReqDto {
    private String name;
    private String email;

    public CustomerCreateReqDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Customer toEntity() {
        return new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
    }
}
