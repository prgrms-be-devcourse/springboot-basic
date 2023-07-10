package com.prgrms.springbootbasic.dto.voucher.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherUpdateRequest {

    private final long discount;
}
