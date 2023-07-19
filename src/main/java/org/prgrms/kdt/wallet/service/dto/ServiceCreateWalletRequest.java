package org.prgrms.kdt.wallet.service.dto;

import java.util.UUID;

public record ServiceCreateWalletRequest(UUID walletId, UUID memberId, UUID voucherId) {
}
