package org.prgrms.kdt.wallet.dto.response;

import org.prgrms.kdt.wallet.domain.JoinedWallet;

import java.util.List;
import java.util.stream.Collectors;

public record JoinedWalletResponses(List<JoinedWalletResponse> wallets) {

    public static JoinedWalletResponses of(List<JoinedWallet> joinedWallets) {
        List<JoinedWalletResponse> walletsResponse = joinedWallets.stream().map(JoinedWalletResponse::new).collect(Collectors.toList());
        return new JoinedWalletResponses(walletsResponse);
    }

    @Override
    public List<JoinedWalletResponse> wallets() {
        return List.copyOf(wallets);
    }
}
