package com.program.commandLine.model;

import com.program.commandLine.model.voucher.VoucherType;

import java.util.UUID;

public class VoucherInputData {

    private final VoucherType voucherType;
    private final int discount;
    private final UUID voucherId;

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscount() {
        return discount;
    }

    public VoucherInputData(String voucherType, String discount) {
        this.voucherType = VoucherType.getType(voucherType);
        this.discount = Integer.parseInt(discount);
        voucherId = UUID.randomUUID();
    }
}
