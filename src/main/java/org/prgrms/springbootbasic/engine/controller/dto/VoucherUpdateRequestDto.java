package org.prgrms.springbootbasic.engine.controller.dto;

import org.prgrms.springbootbasic.exception.VoucherException;

import java.util.Optional;
import java.util.UUID;

public class VoucherUpdateRequestDto {
    private final UUID voucherId;
    private final Integer value;
    private Optional<UUID> customerId;

    public VoucherUpdateRequestDto(String voucherId, Integer value, String customerId) {
        this.value = value;
        try {
            this.voucherId = UUID.fromString(voucherId);
        } catch (IllegalArgumentException ex) {
            throw new VoucherException("Invalid voucher Id.");
        }
        try {
            this.customerId = Optional.of(UUID.fromString(customerId));
        } catch (IllegalArgumentException ex) {
            this.customerId = Optional.empty();
        }
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
