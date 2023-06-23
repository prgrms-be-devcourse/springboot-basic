package com.programmers.voucher.domain;

import com.programmers.voucher.request.VoucherCreateRequest;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, VoucherCreateRequest request) {
        this.voucherId = voucherId;
        this.percent = request.getAmount();
    }

    @Override
    public long totalAmount(long beforeAmount) {
        return beforeAmount - beforeAmount * percent / 100;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String fullInfoString() {
        return "VoucherID: " + voucherId + ", discount: " + percent + "%";
    }
}
