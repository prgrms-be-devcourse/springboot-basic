package com.programmers.vouchermanagement.voucher.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateVoucherRequest(
        UUID voucherId,
        @Min(value = 0, message = "Discount value must be greater than 0.") long discountValue,
        @NotBlank(message = "Voucher type cannot be blank.") String voucherType) {
}
