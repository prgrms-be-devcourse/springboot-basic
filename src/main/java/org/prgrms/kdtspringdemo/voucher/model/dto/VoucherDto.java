package org.prgrms.kdtspringdemo.voucher.model.dto;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;

public class VoucherDto {
    private final VoucherType voucherType;
    private final long discount;

    public VoucherDto(VoucherType voucherType, long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }

    public static VoucherDto toDto(Voucher voucher) {
        return new VoucherDto(
                voucher.getVoucherType(),
                voucher.getDiscount()
        );
    }
}
