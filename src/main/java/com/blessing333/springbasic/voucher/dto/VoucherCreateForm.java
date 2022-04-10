package com.blessing333.springbasic.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VoucherCreateForm{
    String voucherType;
    String discountAmount;
}
