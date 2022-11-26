package com.programmers.voucher.controller.voucher.dto;

import java.util.UUID;

public record VoucherAssignRequest(UUID voucherId, String email) {
}
