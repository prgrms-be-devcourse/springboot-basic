package org.prgrms.kdt.wallet.controller.dto;

import java.util.UUID;

public record CreateWalletApiRequest(UUID memberId, UUID voucherId) {
}
