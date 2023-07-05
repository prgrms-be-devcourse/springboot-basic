package com.programmers.voucher.domain.voucher.domain;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;

import java.util.UUID;

public abstract class Voucher {
    protected final UUID voucherId;

    public Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public abstract VoucherDto toDto();

    public UUID getVoucherId() {
        return voucherId;
    }

    public abstract long totalAmount(long beforeAmount);
}
