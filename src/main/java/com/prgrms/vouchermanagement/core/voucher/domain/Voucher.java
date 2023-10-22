package com.prgrms.vouchermanagement.core.domain;

import java.util.UUID;

public class Voucher {

    private final UUID voucherID;
    private final long amount;
    private final VoucherType voucherType;

    public Voucher(UUID voucherID, long amount, VoucherType voucherType) {
        this.voucherID = voucherID;
        this.amount = amount;
        this.voucherType = voucherType;
    }
}
