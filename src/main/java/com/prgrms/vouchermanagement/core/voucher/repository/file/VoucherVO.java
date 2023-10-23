package com.prgrms.vouchermanagement.core.voucher.repository.file;

import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;

public class VoucherVO {

    private long voucherID;
    private String name;
    private long amount;
    private VoucherType voucherType;

    public VoucherVO() {
    }

    public VoucherVO(long voucherID, String name, long amount, VoucherType voucherType) {
        this.voucherID = voucherID;
        this.name = name;
        this.amount = amount;
        this.voucherType = voucherType;
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
