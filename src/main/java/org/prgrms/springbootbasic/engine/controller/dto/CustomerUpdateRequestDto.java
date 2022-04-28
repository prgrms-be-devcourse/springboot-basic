package org.prgrms.springbootbasic.engine.controller.dto;

import org.prgrms.springbootbasic.exception.VoucherException;

import java.util.Optional;
import java.util.UUID;

public class CustomerUpdateRequestDto {
    private final UUID customerId;
    private final String name;

    public CustomerUpdateRequestDto(String customerId, String name) {
        this.name = name;
        try {
            this.customerId = UUID.fromString(customerId);
        } catch (IllegalArgumentException ex) {
            throw new VoucherException("Invalid Id format.");
        }
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
