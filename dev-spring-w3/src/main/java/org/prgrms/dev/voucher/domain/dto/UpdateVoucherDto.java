package org.prgrms.dev.voucher.domain.dto;

import java.util.UUID;

public class UpdateVoucherDto {
    private UUID voucherId;
    private long discount;

    public UpdateVoucherDto(UUID voucherId, long discount) {
        this.voucherId = voucherId;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscount() {
        return discount;
    }
}
