package org.programmers.kdt.weekly.voucher.controller;

import java.util.UUID;
import org.programmers.kdt.weekly.voucher.model.VoucherType;

public class CreateVoucherRequest {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long value;

    public CreateVoucherRequest(VoucherType voucherType, long value) {
        voucherId = UUID.randomUUID();
        this.voucherType = voucherType;
        this.value = value;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getValue() {
        return value;
    }
}
