package org.weekly.weekly.voucher.dto.request;

import org.weekly.weekly.util.DiscountType;
import org.weekly.weekly.voucher.dto.VoucherInfoRequest;

public class VoucherCreationRequest {
    private final VoucherInfoRequest voucherInfoRequest;
    private final DiscountType discountType;

    public VoucherCreationRequest(VoucherInfoRequest voucherInfoRequest, DiscountType discountType) {
        this.voucherInfoRequest = voucherInfoRequest;
        this.discountType = discountType;
    }
}
