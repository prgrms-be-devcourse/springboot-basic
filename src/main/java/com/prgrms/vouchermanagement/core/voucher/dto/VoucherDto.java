package com.prgrms.vouchermanagement.core.voucher.dto;

import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;

public class VoucherDto {

    private final long id;
    private final String name;
    private final long amount;
    private final VoucherType voucherType;

    public VoucherDto(long id, String name, long amount, VoucherType voucherType) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
