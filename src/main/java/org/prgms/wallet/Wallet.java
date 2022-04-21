package org.prgms.wallet;

import org.prgms.validator.DomainValidators;

import java.util.UUID;

public record Wallet(UUID walletId, UUID customerId, UUID voucherId) {

    public Wallet {
        DomainValidators.notNullAndEmptyCheck(walletId, customerId, voucherId);
    }
}
