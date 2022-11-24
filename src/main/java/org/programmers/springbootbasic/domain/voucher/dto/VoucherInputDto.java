package org.programmers.springbootbasic.domain.voucher.dto;

import org.programmers.springbootbasic.data.VoucherType;


public class VoucherInputDto {
    private VoucherType voucherType;
    private long amount;

    public VoucherInputDto(String type, long amount) {
        this.voucherType = convertToVoucherType(type);
        this.amount = amount;
    }

    private VoucherType convertToVoucherType(String type) {
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
