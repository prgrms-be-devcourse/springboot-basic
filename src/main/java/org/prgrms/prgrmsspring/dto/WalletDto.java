package org.prgrms.prgrmsspring.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class WalletDto {
    private UUID customerId;
    private UUID voucherId;

    public WalletDto(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }
}
