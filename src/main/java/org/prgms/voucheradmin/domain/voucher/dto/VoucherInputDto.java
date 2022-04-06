package org.prgms.voucheradmin.domain.voucher.dto;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

public class VoucherInputDto {
    private VoucherType voucherType;
    private long amount;

    public VoucherInputDto(VoucherType voucherType, long amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}
