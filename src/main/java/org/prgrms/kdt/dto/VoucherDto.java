package org.prgrms.kdt.dto;

public class VoucherDto {

    public final long discountAmount;
    public final int voucherType;

    public VoucherDto(long discountAmount, int voucherType) {
        this.discountAmount = discountAmount;
        this.voucherType = voucherType;
    }
}
