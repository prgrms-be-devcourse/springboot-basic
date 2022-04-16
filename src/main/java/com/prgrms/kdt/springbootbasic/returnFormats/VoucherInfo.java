package com.prgrms.kdt.springbootbasic.returnFormats;

public class VoucherInfo {
    private int voucherType;
    private long discountAmount;

    public VoucherInfo(int voucherType, long discountAmount){
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public int getVoucherType() {
        return voucherType;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }
}
