package org.prgrms.springbootbasic.engine.controller.dto;

import org.prgrms.springbootbasic.exception.VoucherException;

import java.util.Optional;
import java.util.UUID;

import static org.prgrms.springbootbasic.engine.util.UUIDUtil.convertStringToUUID;

public class CustomerUpdateRequestDto {
    private final UUID customerId;
    private final String name;

    public CustomerUpdateRequestDto(String customerId, String name) {
        this.name = name;
        this.customerId = convertStringToUUID(customerId);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
