package com.programmers.springweekly.dto.wallet.response;

import lombok.Getter;

import java.util.List;

@Getter
public class WalletsResponse {

    private final List<WalletResponse> walletList;

    public WalletsResponse(List<WalletResponse> walletList) {
        this.walletList = walletList;
    }
}
