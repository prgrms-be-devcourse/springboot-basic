package com.programmers.springmission.voucher.presentation.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherUpdateRequest {

    private final long amount;
}
