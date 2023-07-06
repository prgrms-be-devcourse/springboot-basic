package com.devcourse.voucherapp.entity.dto;

import com.devcourse.voucherapp.entity.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VoucherCreateRequestDto {

    private final VoucherType type;
    private final String discountAmount;
}
