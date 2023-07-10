package com.prgms.VoucherApp.domain.wallet.dto;

import java.util.List;

public record WalletsResponse(
    List<WalletResponse> wallets
) {

    public long getSize() {
        return wallets.size();
    }
}
