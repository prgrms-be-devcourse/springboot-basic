package com.programmers.springbootbasic.service.dto.Voucher;


import com.programmers.springbootbasic.domain.model.Duration;

public record VoucherResponse(
        String name,
        Long minimumPriceCondition,
        Duration duration
) {

}
