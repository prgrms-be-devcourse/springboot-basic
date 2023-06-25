package com.programmers.voucher.dto.reponse;

import com.programmers.voucher.domain.Voucher;

public class ResponseFactory {
    public static VoucherInfoResponse createVoucherInfoResponse(Voucher voucher) {
        return new VoucherInfoResponse(voucher.getVoucherId(), voucher.getDiscountAmount());
    }
}
