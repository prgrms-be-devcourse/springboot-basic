package org.prgrms.kdtspringdemo.wallet.controller;

import org.prgrms.kdtspringdemo.wallet.service.WalletService;
import org.springframework.stereotype.Controller;

@Controller
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
}
