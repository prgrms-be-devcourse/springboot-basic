package com.prgrms.springbasic.domain.wallet.dto;

import java.util.UUID;

public record WalletRequest(UUID customerId, UUID voucherId) {
}
