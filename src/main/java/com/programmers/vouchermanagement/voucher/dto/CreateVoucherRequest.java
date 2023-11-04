package com.programmers.vouchermanagement.voucher.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateVoucherRequest(
        @Positive(message = "Discount value must be greater than 0.") long discountValue,
        @NotBlank(message = "Voucher type cannot be blank.") String voucherType
) {
}
