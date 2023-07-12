package org.prgrms.kdt.wallet.dto.response;

import org.prgrms.kdt.wallet.domain.Wallet;

import java.util.List;
import java.util.stream.Collectors;

public class WalletsResponse {
    private final List<WalletResponse> wallets;

    public WalletsResponse(List<WalletResponse> wallets) {
        this.wallets = wallets;
    }

    public static WalletsResponse of(List<Wallet> wallets) {
        List<WalletResponse> walletsResponse = wallets.stream().map(WalletResponse::new).collect(Collectors.toList());
        return new WalletsResponse(walletsResponse);
    }

    public List<WalletResponse> getWallets(){
        return List.copyOf(wallets);
    }
}
