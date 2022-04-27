package com.blessing333.springbasic.domain.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherCreateFormPayload {
    @NotBlank
    private String voucherType;
    @NotBlank
    private String discountAmount;
}
