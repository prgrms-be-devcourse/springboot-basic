package org.prgrms.kdt.wallet.dto.response;

import org.prgrms.kdt.wallet.domain.JoinedWallet;

import java.util.List;
import java.util.stream.Collectors;

public class JoinedWalletsResponse {
    private final List<JoinedWalletResponse> wallets;

    public JoinedWalletsResponse(List<JoinedWalletResponse> wallets) {
        this.wallets = wallets;
    }

    public static JoinedWalletsResponse of(List<JoinedWallet> joinedWallets) {
        List<JoinedWalletResponse> walletsResponse = joinedWallets.stream().map(JoinedWalletResponse::new).collect(Collectors.toList());
        return new JoinedWalletsResponse(walletsResponse);
    }

    public List<JoinedWalletResponse> getWallets(){
        return List.copyOf(wallets);
    }
}
