package com.prgrms.vouchermanagement.core.voucher.repository.file;

import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;

import java.util.Objects;

public class VoucherVO {

    private String voucherID;
    private String name;
    private long amount;
    private VoucherType voucherType;

    public VoucherVO() {
    }

    public VoucherVO(String voucherID, String name, long amount, VoucherType voucherType) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherVO voucherVO = (VoucherVO) o;
        return amount == voucherVO.amount && Objects.equals(voucherID, voucherVO.voucherID) && Objects.equals(name, voucherVO.name) && voucherType == voucherVO.voucherType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherID, name, amount, voucherType);
    }
}
