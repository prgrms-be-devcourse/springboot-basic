package com.prgrms.vouchermanagement.core.voucher.domain;

import java.util.UUID;

public class Voucher {

    private final String voucherID;
    private final String name;
    private final long amount;
    private final VoucherType voucherType;

    public Voucher(String name, long amount, VoucherType voucherType) {
        this.voucherID = UUID.randomUUID().toString();
        this.name = name;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public Voucher(String voucherID, String name, long amount, VoucherType voucherType) {
        this.voucherID = voucherID;
        this.name = name;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public String getVoucherID() {
        return voucherID;
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
