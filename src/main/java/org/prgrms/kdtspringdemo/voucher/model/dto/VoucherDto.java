package org.prgrms.kdtspringdemo.voucher.model.dto;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;

public class VoucherDto {
    private final VoucherType voucherType;
    private final long amount;

    public VoucherDto(VoucherType voucherType, long amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }

    public static VoucherDto toDto(Voucher voucher) {
        return new VoucherDto(
                voucher.getVoucherType(),
                voucher.getDiscount()
        );
    }
}
