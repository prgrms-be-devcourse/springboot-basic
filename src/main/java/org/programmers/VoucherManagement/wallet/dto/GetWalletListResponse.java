package org.programmers.VoucherManagement.wallet.dto;

import org.programmers.VoucherManagement.wallet.domain.Wallet;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetWalletListResponse {
    private final List<GetWalletResponse> walletResponses;

    public GetWalletListResponse(List<Wallet> wallets) {
        this.walletResponses = wallets
                .stream()
                .map(GetWalletResponse::toDto).collect(Collectors.toList());
    }

    public List<GetWalletResponse> getGetWalletListRes() {
        return Collections.unmodifiableList(walletResponses);
    }
}

