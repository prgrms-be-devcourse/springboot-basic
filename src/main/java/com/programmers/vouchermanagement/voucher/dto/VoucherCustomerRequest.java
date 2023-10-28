package com.programmers.vouchermanagement.voucher.dto;

import java.util.UUID;

public record VoucherCustomerRequest(UUID voucherId, UUID customerId) {
}
