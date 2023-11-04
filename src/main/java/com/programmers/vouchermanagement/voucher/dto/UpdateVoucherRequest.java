package com.programmers.vouchermanagement.voucher.dto;

import java.util.UUID;

public record UpdateVoucherRequest(UUID voucherId, long discountValue, String voucherType) {
}
