package com.programmers.application.dto.request;

public class RequestFactory {
    public static VoucherCreationRequest createVoucherCreationRequest(String voucherType, long discountAmount) {
        return new VoucherCreationRequest(voucherType, discountAmount);
    }
}
