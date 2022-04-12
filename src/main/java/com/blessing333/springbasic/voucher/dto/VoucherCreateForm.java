package com.blessing333.springbasic.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherCreateForm{
    String voucherType;
    String discountAmount;
}
