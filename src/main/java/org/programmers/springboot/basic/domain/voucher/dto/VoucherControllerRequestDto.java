package org.programmers.springboot.basic.domain.voucher.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherControllerRequestDto {

    private final String voucherId;
    private final String discount;
    private final String voucherType;
    private final String createdAt;
}
