package com.mountain.voucherApp.application.port.in;

import java.util.UUID;

public class VoucherIdUpdateDto {
    private UUID customerId;
    private UUID voucherId;

    public VoucherIdUpdateDto(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }
}
