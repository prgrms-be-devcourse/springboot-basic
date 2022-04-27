package com.blessing333.springbasic.domain.voucher.dto;

import com.blessing333.springbasic.domain.voucher.model.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherCreateForm {
    private Voucher.VoucherType voucherType;
    private long discountAmount;
}
