package org.prgms.kdtspringweek1.wallet.service.dto;

import org.prgms.kdtspringweek1.wallet.entity.Wallet;

import java.util.UUID;

public class CreateWalletRequestDto {
    private UUID voucherId;
    private UUID customerId;

    public CreateWalletRequestDto(UUID voucherId, UUID customerId) {
        this.voucherId = voucherId;
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Wallet toWallet() {
        return Wallet.createWithVoucherIdAndCustomerId(voucherId, customerId);
    }
}
