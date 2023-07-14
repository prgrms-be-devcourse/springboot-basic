package com.programmers.springbootbasic.service.dto;


import com.programmers.springbootbasic.domain.voucher.Duration;

public record VoucherResponse(
        String name,
        Long minimumPriceCondition,
        Duration duration
) {

}
