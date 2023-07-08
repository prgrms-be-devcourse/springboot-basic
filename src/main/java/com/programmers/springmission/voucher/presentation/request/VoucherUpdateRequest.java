package com.programmers.springmission.voucher.presentation.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherUpdateRequest {

    private final UUID voucherId;
    private final long amount;

    public VoucherUpdateRequest(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }
}
