package com.example.voucher.service.wallet;

import java.util.UUID;

public record WalletDTO(UUID walletId, UUID customerId, UUID voucherId) {
}
