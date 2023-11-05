package com.programmers.springbasic.entity.wallet;

import java.util.UUID;

public record Wallet(long walletId, UUID customerId, UUID voucherId) {
}
