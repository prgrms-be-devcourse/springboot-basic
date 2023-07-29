package org.prgrms.kdt.wallet.service.dto;

import java.util.UUID;

public record CreateWalletRequest(UUID memberId, UUID voucherId) {
}
