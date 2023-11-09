package org.programmers.springorder.voucher.dto;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record GiveVoucherRequestDto(
        @NonNull UUID voucherId,
        @NonNull UUID customerId) {


}
