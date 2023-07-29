package com.tangerine.voucher_system.application.wallet.model;

import java.util.UUID;

public record Wallet(
        UUID walletId,
        UUID voucherId,
        UUID customerId
) {
}
