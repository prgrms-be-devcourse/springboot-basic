package com.devcourse.voucherapp.entity.voucher.request;

import com.devcourse.voucherapp.entity.voucher.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VoucherCreateRequestDto {

    private final VoucherType type;
    private final String discountAmount;
}
