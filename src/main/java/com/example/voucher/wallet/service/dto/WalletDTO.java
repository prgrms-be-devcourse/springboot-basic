package com.example.voucher.wallet.service.dto;

import java.util.UUID;

public record WalletDTO(UUID walletId, UUID customerId, UUID voucherId) {
}
