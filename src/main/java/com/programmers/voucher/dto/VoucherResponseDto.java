package com.programmers.voucher.dto;

import com.programmers.voucher.domain.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherResponseDto(UUID voucherId, Discount discount, LocalDateTime createdDate) {
}
