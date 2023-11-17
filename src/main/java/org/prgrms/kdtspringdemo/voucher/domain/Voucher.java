package org.prgrms.kdtspringdemo.voucher.domain;

import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final VoucherPolicy voucherPolicy;

    public Voucher(UUID voucherId, VoucherPolicy voucherPolicy) {
        this.voucherId = voucherId;
        this.voucherPolicy = voucherPolicy;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }

    public String toString() {
        return "=======================\n"+
                "voucherId : "+voucherId+"\n"+
                "voucherPolicy : "+voucherPolicy.getVoucherType()+"\n";
    }
}
