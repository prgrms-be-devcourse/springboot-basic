package com.programmers.springweekly.dto.wallet.response;

import java.util.List;
import lombok.Getter;

@Getter
public class WalletsResponse {

    private final List<WalletResponse> walletList;

    public WalletsResponse(List<WalletResponse> walletList) {
        this.walletList = walletList;
    }

}
