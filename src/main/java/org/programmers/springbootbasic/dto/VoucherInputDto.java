package org.programmers.springbootbasic.dto;

import org.programmers.springbootbasic.data.VoucherType;


public class VoucherInputDto {
    private String type;
    private VoucherType voucherType;
    private long amount;

    public VoucherInputDto(String type, long amount) {
        this.type = type;
        this.voucherType = convertToVoucherType();
        this.amount = amount;
    }

    private VoucherType convertToVoucherType() {
        // valid 하다면 VoucherType 으로 convert.
        return VoucherType.valueOfType(type);
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}
