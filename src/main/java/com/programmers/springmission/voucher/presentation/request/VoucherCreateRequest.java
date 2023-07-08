package com.programmers.springmission.voucher.presentation.request;

import com.programmers.springmission.voucher.domain.enums.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherCreateRequest {

    private final VoucherType voucherType;
    private final long amount;
}

