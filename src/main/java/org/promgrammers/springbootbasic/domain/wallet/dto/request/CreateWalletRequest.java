package org.promgrammers.springbootbasic.domain.wallet.dto.request;

import java.util.UUID;

public record CreateWalletRequest(UUID voucherId, UUID customerId) {

}
