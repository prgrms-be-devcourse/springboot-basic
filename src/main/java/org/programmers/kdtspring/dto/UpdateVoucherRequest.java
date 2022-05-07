package org.programmers.kdtspring.dto;

import java.util.UUID;

public class UpdateVoucherRequest {
    private final UUID customerId;

    public UpdateVoucherRequest(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}