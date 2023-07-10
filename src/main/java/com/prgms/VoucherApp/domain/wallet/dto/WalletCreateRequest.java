package com.prgms.VoucherApp.domain.wallet.dto;

import java.util.UUID;

public record WalletCreateRequest(
    UUID customerId,
    UUID voucherId
) {
}


