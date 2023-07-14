package org.prgrms.kdt.wallet.dto.response;

import org.prgrms.kdt.wallet.domain.JoinedWallet;

import java.util.List;
import java.util.stream.Collectors;

public record JoinedWalletsResponse(List<JoinedWalletResponse> wallets) {

    public static JoinedWalletsResponse of(List<JoinedWallet> joinedWallets) {
        List<JoinedWalletResponse> walletsResponse = joinedWallets.stream().map(JoinedWalletResponse::new).collect(Collectors.toList());
        return new JoinedWalletsResponse(walletsResponse);
    }

    @Override
    public List<JoinedWalletResponse> wallets() {
        return List.copyOf(wallets);
    }
}
