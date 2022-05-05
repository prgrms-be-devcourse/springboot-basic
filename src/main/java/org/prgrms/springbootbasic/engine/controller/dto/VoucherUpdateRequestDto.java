package org.prgrms.springbootbasic.engine.controller.dto;

import org.prgrms.springbootbasic.exception.VoucherException;

import java.util.Optional;
import java.util.UUID;

public class VoucherUpdateRequestDto {
    private final UUID voucherId;
    private final Integer value;
    private final Optional<UUID> customerId;

    public VoucherUpdateRequestDto(String voucherId, Integer value, String customerId) {
        UUID id = null;
        this.value = value;
        try {
            this.voucherId = UUID.fromString(voucherId);
        } catch (IllegalArgumentException ex) {
            throw new VoucherException("Invalid Id format.");
        }
        try {
            id = UUID.fromString(customerId);
        } catch (IllegalArgumentException ignored) {
        }
        this.customerId = Optional.ofNullable(id);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Integer getValue() {
        return value;
    }

    public Optional<UUID> getCustomerId() {
        return customerId;
    }
}
