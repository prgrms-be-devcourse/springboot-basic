package com.prgms.vouchermanager.dto;

import lombok.Getter;

@Getter
public class UpdateVoucherDto {
    private final long value;
    private final int VoucherType;

    public UpdateVoucherDto(long value, int voucherType) {
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
