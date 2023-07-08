package org.prgrms.kdt.wallet.dto;

import java.util.UUID;

public record CreateWalletRequest(UUID memberId, UUID voucherId) {
}
