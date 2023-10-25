package com.zerozae.voucher.dto.wallet;

import com.zerozae.voucher.domain.wallet.Wallet;
import lombok.Getter;

import java.util.UUID;

@Getter
public class WalletResponse {

    private UUID customerId;
    private UUID voucherId;

    public WalletResponse(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public static WalletResponse toDto(Wallet wallet) {
        return new WalletResponse(
                wallet.getCustomerId(),
                wallet.getVoucherId()
        );
    }
}
