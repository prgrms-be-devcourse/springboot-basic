package com.prgrms.voucher_manager.voucher.controller;

import java.util.UUID;

public class VoucherDto {

    private UUID voucherId;
    private String type;
    private long value;

    public VoucherDto() {}

    public VoucherDto(UUID voucherId, String type, long value) {
        this.voucherId = voucherId;
        this.type = type;
        this.value = value;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getType() {
        return type;
    }

    public long getValue() {
        return value;
    }
}
