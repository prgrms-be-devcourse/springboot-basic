package com.example.voucher.wallet.controller.model;

import java.util.Arrays;
import java.util.List;
import com.example.voucher.wallet.service.dto.WalletDTO;

public class WalletResponse {

    private final List<WalletDTO> wallets;

    public WalletResponse(List<WalletDTO> wallets) {
        this.wallets = wallets;
    }

    public WalletResponse(WalletDTO wallets) {
        this.wallets = Arrays.asList(wallets);
    }

    public List<WalletDTO> getWallets() {
        return wallets;
    }
}
