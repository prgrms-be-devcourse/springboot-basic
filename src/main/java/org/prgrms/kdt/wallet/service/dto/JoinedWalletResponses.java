package org.prgrms.kdt.wallet.service.dto;

import org.prgrms.kdt.wallet.domain.QueryWallet;

import java.util.List;
import java.util.stream.Collectors;

public record JoinedWalletResponses(List<JoinedWalletResponse> wallets) {

    public static JoinedWalletResponses of(List<QueryWallet> queryWallets) {
        List<JoinedWalletResponse> walletsResponse = queryWallets.stream().map(JoinedWalletResponse::new).collect(Collectors.toList());
        return new JoinedWalletResponses(walletsResponse);
    }

    @Override
    public List<JoinedWalletResponse> wallets() {
        return List.copyOf(wallets);
    }
}
