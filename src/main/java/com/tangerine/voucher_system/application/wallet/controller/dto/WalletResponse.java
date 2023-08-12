package com.tangerine.voucher_system.application.wallet.controller.dto;

import java.util.UUID;

public record WalletResponse(
        UUID walletId,
        UUID voucherId,
        UUID customerId
) {
}
