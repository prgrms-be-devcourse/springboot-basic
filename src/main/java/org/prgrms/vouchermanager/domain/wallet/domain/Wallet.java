package org.prgrms.vouchermanager.domain.wallet.domain;

import lombok.Getter;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
public class Wallet {
    private final UUID id = UUID.randomUUID();
    private final UUID voucherId;
    private final UUID customerId;

    public Wallet(UUID customerId, UUID voucherId) {
        checkNotNull(customerId);
        checkNotNull(voucherId);

        this.customerId = customerId;
        this.voucherId = voucherId;
    }
}
