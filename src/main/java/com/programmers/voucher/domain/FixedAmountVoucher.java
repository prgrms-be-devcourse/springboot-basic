package com.programmers.voucher.domain;

import com.programmers.voucher.request.VoucherCreationRequest;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, VoucherCreationRequest voucherCreationRequest) {
        this.voucherId = voucherId;
        this.amount = voucherCreationRequest.getAmount();
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeAmount) {
        return beforeAmount - amount;
    }
}
