package com.prgms.vouchermanager.dto;

public class CreateVoucherDto {

    private final long value;
    private final int VoucherType;

    public CreateVoucherDto(long value, int voucherType) {
        this.value = value;
        this.VoucherType = voucherType;
    }

    public long getValue() {
        return value;
    }

    public int getVoucherType() {
        return VoucherType;
    }
}
