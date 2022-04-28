package com.prgrms.kdt.springbootbasic.returnFormats;

public class VoucherInfo {
    private final String voucherType;
    private final long discountAmount;

    public VoucherInfo(String voucherType, long discountAmount){
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }
}
