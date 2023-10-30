package com.programmers.vouchermanagement.dto.voucher.request;

import java.util.UUID;

// PATCH
public record UpdateVoucherRequestDto(UUID id, long amount) {
}
