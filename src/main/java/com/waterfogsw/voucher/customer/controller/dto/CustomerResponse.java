package com.waterfogsw.voucher.customer.controller.dto;

import com.waterfogsw.voucher.customer.model.Customer;

import java.time.LocalDateTime;

public record CustomerResponse(
        long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(),
                customer.getCreatedAt(), customer.getUpdatedAt());
    }
}
