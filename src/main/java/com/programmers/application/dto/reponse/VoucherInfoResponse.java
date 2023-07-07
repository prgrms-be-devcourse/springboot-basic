package com.programmers.application.dto.reponse;

import java.util.UUID;

public record VoucherInfoResponse(UUID voucherId, long discountAmount) {
}
