package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final VoucherPolicy voucherPolicy;

    public Voucher(UUID voucherId, VoucherType voucherType, VoucherPolicy voucherPolicy) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.voucherPolicy = voucherPolicy;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }
}
