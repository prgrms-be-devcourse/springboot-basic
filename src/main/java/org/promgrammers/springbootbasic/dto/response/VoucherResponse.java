package org.promgrammers.springbootbasic.dto.response;

import org.promgrammers.springbootbasic.domain.VoucherType;

import java.util.UUID;

public class VoucherResponse {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long amount;

    public VoucherResponse(UUID voucherId, VoucherType voucherType, long amount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}
