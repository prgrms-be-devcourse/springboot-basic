package org.programers.vouchermanagement.wallet.dto;

import java.util.List;

public class WalletsResponse {

    private final List<WalletResponse> wallets;

    public WalletsResponse(List<WalletResponse> wallets) {
        this.wallets = wallets;
    }

    public List<WalletResponse> getWallets() {
        return wallets;
    }
}
