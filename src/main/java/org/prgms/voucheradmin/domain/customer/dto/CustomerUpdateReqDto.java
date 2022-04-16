package org.prgms.voucheradmin.domain.customer.dto;

import org.prgms.voucheradmin.domain.customer.entity.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerUpdateReqDto {
    private UUID customerId;
    private String name;

    public CustomerUpdateReqDto(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
