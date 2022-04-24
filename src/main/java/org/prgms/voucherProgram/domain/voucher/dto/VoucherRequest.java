package org.prgms.voucherProgram.domain.voucher.dto;

public class VoucherRequest {
    private int type;
    private long discountValue;

    public VoucherRequest(int type, long discountValue) {
        this.type = type;
        this.discountValue = discountValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(long discountValue) {
        this.discountValue = discountValue;
    }
}
