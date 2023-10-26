package com.prgrms.vouchermanagement.core.voucher.domain;

public class Voucher {

    private long voucherID;
    private final String name;
    private final long amount;
    private final VoucherType voucherType;

    public Voucher(long voucherID, String name, long amount, VoucherType voucherType) {
        this.voucherID = voucherID;
        this.name = name;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public Voucher(String name, long amount, VoucherType voucherType) {
        this.name = name;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public void setVoucherID(long voucherID) {
        this.voucherID = voucherID;
    }

    public long getVoucherID() {
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
