package com.blessing333.springbasic.voucher.dto;

import com.blessing333.springbasic.voucher.domain.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConvertedVoucherCreateForm {
    Voucher.VoucherType voucherType;
    long discountAmount;
}
