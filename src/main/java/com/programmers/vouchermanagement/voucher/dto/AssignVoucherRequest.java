package com.programmers.vouchermanagement.voucher.dto;

import java.util.UUID;

public record AssignVoucherRequest(UUID voucherId, UUID customerId) {
}
