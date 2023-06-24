package com.programmers.voucher.request;

import java.util.Objects;

public class VoucherCreationRequest {
    private final String voucherType;
    private final long discountAmount;

    public VoucherCreationRequest(String voucherType, long discountAmount) {
        validateVoucherCreationRequest(voucherType, discountAmount);
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    private static void validateVoucherCreationRequest(String voucherType, long discountAmount) {
        if(Objects.isNull(voucherType) || discountAmount == 0) {
            throw new IllegalArgumentException("바우처 타입과 할인양을 입력해주세요.");
        }
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

}
