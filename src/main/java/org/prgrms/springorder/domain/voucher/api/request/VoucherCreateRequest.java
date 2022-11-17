package org.prgrms.springorder.domain.voucher.api.request;

import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.console.io.Request;

public class VoucherCreateRequest implements Request {

    private final VoucherType voucherType;

    private final long discountAmount;

    public VoucherCreateRequest(VoucherType voucherType, long discountAmount) {
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public static VoucherCreateRequest of(VoucherType voucherType, long discountAmount) {
        return new VoucherCreateRequest(voucherType, discountAmount);
    }

}
