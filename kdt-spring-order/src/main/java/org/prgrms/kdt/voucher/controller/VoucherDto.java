package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.model.VoucherType;

import javax.validation.constraints.NotNull;

public class VoucherDto {
    @NotNull
    private final long value;
    @NotNull
    private final VoucherType type;

    public VoucherDto(long value, VoucherType type) {
        this.value = value;
        this.type = type;
    }

    public long getValue() {
        return value;
    }
    public VoucherType getType() {
        return type;
    }
}
