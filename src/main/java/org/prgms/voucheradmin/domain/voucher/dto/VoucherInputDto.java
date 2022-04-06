package org.prgms.voucheradmin.domain.voucher.dto;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes;

public class VoucherInputDto {
    private VoucherTypes voucherTypes;
    private long amount;

    public VoucherInputDto(VoucherTypes voucherTypes, long amount) {
        this.voucherTypes = voucherTypes;
        this.amount = amount;
    }

    public VoucherTypes getVoucherType() {
        return voucherTypes;
    }

    public long getAmount() {
        return amount;
    }
}
