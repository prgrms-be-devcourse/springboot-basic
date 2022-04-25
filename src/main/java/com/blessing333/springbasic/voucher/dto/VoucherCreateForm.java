package com.blessing333.springbasic.voucher.dto;

import com.blessing333.springbasic.voucher.domain.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VoucherCreateForm {
    Voucher.VoucherType voucherType;
    long discountAmount;
}
