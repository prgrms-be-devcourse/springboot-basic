package com.prgrms.springbasic.domain.wallet.dto;

import com.prgrms.springbasic.domain.wallet.entity.Wallet;

import java.util.UUID;

public record WalletResponse(UUID wallet_id, UUID customer_id, UUID voucher_id) {
    public static WalletResponse from(Wallet wallet) {
        return new WalletResponse(
                wallet.getWallet_id(),
                wallet.getCustomer_id(),
                wallet.getVoucher_id()
        );
    }
}
