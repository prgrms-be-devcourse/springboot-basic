package org.prgrms.kdt.wallet.dto.request;

import java.util.UUID;

public record CreateWalletRequest(UUID memberId, UUID voucherId) {
}
