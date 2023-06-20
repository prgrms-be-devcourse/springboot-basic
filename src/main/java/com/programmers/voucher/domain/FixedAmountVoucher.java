package com.programmers.voucher.domain;

import com.programmers.voucher.request.VoucherCreateRequest;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, VoucherCreateRequest request) {
        this.voucherId = voucherId;
        this.amount = request.getAmount();
    }

    @Override
    public long totalAmount(long beforeAmount) {
        return beforeAmount - amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
