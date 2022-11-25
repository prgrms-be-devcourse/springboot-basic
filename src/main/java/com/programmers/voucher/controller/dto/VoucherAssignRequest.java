package com.programmers.voucher.controller.dto;

import java.util.UUID;

public record VoucherAssignRequest(UUID voucherId, String email) {
}
