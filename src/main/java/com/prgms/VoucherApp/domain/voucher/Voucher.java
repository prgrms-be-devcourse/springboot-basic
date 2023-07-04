package com.prgms.VoucherApp.domain.voucher;

import java.math.BigDecimal;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private VoucherPolicy voucherPolicy;
    private VoucherType voucherType;

    public Voucher(UUID voucherId, VoucherPolicy voucherPolicy, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.voucherPolicy = voucherPolicy;
        this.voucherType = voucherType;
    }

    public BigDecimal discount(BigDecimal beforeAmount) {
        return voucherPolicy.discount(beforeAmount);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
