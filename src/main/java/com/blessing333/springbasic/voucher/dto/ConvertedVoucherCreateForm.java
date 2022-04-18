package com.blessing333.springbasic.voucher.dto;

import com.blessing333.springbasic.voucher.VoucherType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConvertedVoucherCreateForm {
    VoucherType voucherType;
    long discountAmount;
}
