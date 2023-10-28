package com.zerozae.voucher.dto.wallet;

import com.zerozae.voucher.domain.wallet.Wallet;
import lombok.Getter;

import java.util.UUID;

@Getter

public class WalletCreateRequest {
    private UUID customerId;
    private UUID voucherId;

    public WalletCreateRequest(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public Wallet to() {
        return new Wallet(customerId,voucherId);
    }
}
