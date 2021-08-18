package org.prgrms.kdt.dto;

public class VoucherSaveRequestDto {

    private VoucherType voucherType;
    private int discount;

    public VoucherSaveRequestDto(VoucherType voucherType, int discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscount() {
        return discount;
    }

}