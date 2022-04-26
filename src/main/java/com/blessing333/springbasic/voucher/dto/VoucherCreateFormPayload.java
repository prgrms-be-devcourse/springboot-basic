package com.blessing333.springbasic.voucher.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
public class VoucherCreateFormPayload {
    @NotBlank
    private String voucherType;
    @NotBlank
    private String discountAmount;
}
