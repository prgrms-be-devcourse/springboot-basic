package com.programmers.application.dto.reponse;

import com.programmers.application.domain.voucher.Voucher;

public class ResponseFactory {

    private ResponseFactory() {}

    public static VoucherInfoResponse createVoucherInfoResponse(Voucher voucher) {
        return new VoucherInfoResponse(voucher.getVoucherId(), voucher.getDiscountAmount());
    }
}
