package org.prgrms.springbasic.controller.api.request;

import lombok.Getter;
import org.prgrms.springbasic.domain.voucher.VoucherType;

@Getter
public class VoucherRequest {

    private final VoucherType voucherType;

    private final long discountInfo;

    public VoucherRequest(VoucherType voucherType, long discountInfo) {
        this.voucherType = voucherType;
        this.discountInfo = discountInfo;
    }
}
