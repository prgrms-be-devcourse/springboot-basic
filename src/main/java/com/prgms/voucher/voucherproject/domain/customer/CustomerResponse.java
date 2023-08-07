package com.prgms.voucher.voucherproject.domain.customer;

import builder.builderEntity.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerResponse implements Entity {
    private final UUID customer_id;
    private final String email;
    private String name;
    private final LocalDateTime created_at;

    private CustomerResponse(UUID customerId, String email, String name, LocalDateTime createdAt) {
        customer_id = customerId;
        this.email = email;
        this.name = name;
        created_at = createdAt;
    }
}
