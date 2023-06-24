package org.prgrms.kdtspringdemo.voucher.model.vo;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;

public class VoucherVO {
    private final VoucherType voucherType;
    private final long discount;

    public VoucherVO(VoucherType voucherType, long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }
}
