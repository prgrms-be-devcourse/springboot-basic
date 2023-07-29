package com.programmers.springbootbasic.service.dto.Voucher;


import com.programmers.springbootbasic.domain.voucher.VoucherType;

import java.util.UUID;

public record VoucherResponse(
        UUID voucherId,
        VoucherType voucherType,
        int amountOrPercent
) {

}
