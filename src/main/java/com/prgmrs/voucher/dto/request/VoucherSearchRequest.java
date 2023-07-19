package com.prgmrs.voucher.dto.request;

import java.time.LocalDateTime;

public record VoucherSearchRequest(LocalDateTime startDate, LocalDateTime endDate, String discountType) {
}
