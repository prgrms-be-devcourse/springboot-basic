package com.prgmrs.voucher.model;

import java.util.UUID;

public record Wallet(UUID userId, UUID voucherId) {
}
