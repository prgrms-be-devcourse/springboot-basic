package com.prgms.vouchermanager.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateWalletDto {
    private final Long customerId;
    private final UUID voucherId;

    public CreateWalletDto(Long customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

}
