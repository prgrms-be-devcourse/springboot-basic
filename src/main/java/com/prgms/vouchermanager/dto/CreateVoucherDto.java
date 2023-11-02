package com.prgms.vouchermanager.dto;

public class CreateVoucherDto {

    private final long value;
    private final int voucherType;

    public CreateVoucherDto(long value, int voucherType) {
        this.value = value;
        this.voucherType = voucherType;
    }

    public long getValue() {
        return value;
    }

    public int getVoucherType() {
        return voucherType;
    }
}
