package org.prgrms.kdtspringdemo.voucher.model.dto;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;

import java.util.UUID;

public class VoucherResponse {
    private final UUID id;
    private final VoucherType type;
    private final long amount;

    public VoucherResponse(UUID id, VoucherType type, long amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}
