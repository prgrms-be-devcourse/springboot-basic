package com.blessing333.springbasic.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class VoucherCreateFormPayload {
    @NotBlank
    String voucherType;
    @NotBlank
    String discountAmount;
}
