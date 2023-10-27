package org.programmers.springorder.voucher.dto;

import java.util.UUID;

public class GiveVoucherRequestDto {
    private final UUID VoucherId;

    private final UUID customerId;
    public GiveVoucherRequestDto(UUID voucherId, UUID customerId) {
        VoucherId = voucherId;
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return VoucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
