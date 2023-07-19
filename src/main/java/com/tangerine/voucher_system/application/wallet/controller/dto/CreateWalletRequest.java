package com.tangerine.voucher_system.application.wallet.controller.dto;

import java.util.UUID;

public record CreateWalletRequest(
        UUID voucherId,
        UUID customerId
) {
}
