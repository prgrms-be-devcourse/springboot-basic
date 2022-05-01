package org.prgrms.voucherapp.engine.wallet.dto;

import java.util.UUID;

public record WalletDto(UUID voucherId, UUID customerId) {
}
