package com.programmers.springbootbasic.service.dto;

import java.time.LocalDateTime;

public record VoucherResponse(
        String name,
        Long minimumPriceCondition,
        LocalDateTime createdDate,
        LocalDateTime expirationDate
) {

}
