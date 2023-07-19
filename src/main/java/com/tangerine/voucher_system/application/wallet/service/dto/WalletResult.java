package com.tangerine.voucher_system.application.wallet.service.dto;

import java.util.UUID;

public record WalletResult(
        UUID walletId,
        UUID voucherId,
        UUID customerId
) {
}
