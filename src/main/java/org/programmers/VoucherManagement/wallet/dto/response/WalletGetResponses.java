package org.programmers.VoucherManagement.wallet.dto.response;

import org.programmers.VoucherManagement.wallet.domain.Wallet;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class WalletGetResponses {
    private final List<WalletGetResponse> walletResponses;

    public WalletGetResponses(List<Wallet> wallets) {
        this.walletResponses = wallets
                .stream()
                .map(WalletGetResponse::toDto).collect(Collectors.toList());
    }

    public List<WalletGetResponse> getGetWalletListRes() {
        return Collections.unmodifiableList(walletResponses);
    }
}
