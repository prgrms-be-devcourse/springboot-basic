package com.devcourse.voucherapp.entity.voucher.dto;

import com.devcourse.voucherapp.entity.voucher.VoucherType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherCreateRequestDto {

    private VoucherType type;
    private String discountAmount;
}
