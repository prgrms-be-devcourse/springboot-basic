package com.prgmrs.voucher.dto.response;

import com.prgmrs.voucher.model.Wallet;

public class WalletResponse {
    private final Wallet wallet;
    private final String username;

    public WalletResponse(Wallet wallet, String username) {
        this.wallet = wallet;
        this.username = username;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getUsername() { return username; }
}
