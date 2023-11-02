package com.programmers.vouchermanagement.voucher.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateVoucherRequest(@Positive long discountValue, @NotBlank String voucherType) {
}
