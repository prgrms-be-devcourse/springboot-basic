package com.programmers.voucher.controller;

import com.programmers.voucher.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void assign(UUID voucherId, String email) {
        walletService.assign(voucherId, email);
    }
}
