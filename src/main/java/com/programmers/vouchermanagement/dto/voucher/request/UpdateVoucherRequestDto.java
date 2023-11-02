package com.programmers.vouchermanagement.dto.voucher.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

// PATCH
@Data
public class UpdateVoucherRequestDto {
    @Min(value = 0, message = "Amount should be equal or greater than 0")
    @Max(value = 100, message = "Amount should be equal or less than 100")
    private long amount;
}
