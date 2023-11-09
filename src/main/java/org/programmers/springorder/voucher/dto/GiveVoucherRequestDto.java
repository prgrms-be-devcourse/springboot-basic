package org.programmers.springorder.voucher.dto;

import java.util.UUID;

public class GiveVoucherRequestDto {
    private final UUID voucherId;

    private final UUID customerId;
    public GiveVoucherRequestDto(UUID voucherId, UUID customerId) {
        this.voucherId = voucherId;
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
