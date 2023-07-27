package org.programmers.VoucherManagement.voucher.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record VoucherUpdateRequestData(@NotNull @Min(value = 0, message = "할인 가격은 0보다 작을 수 없습니다.") int discountValue) {
}
