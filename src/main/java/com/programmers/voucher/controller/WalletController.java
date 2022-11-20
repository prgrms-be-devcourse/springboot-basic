package com.programmers.voucher.controller;

import com.programmers.voucher.io.View;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class WalletController {
    private final WalletService walletService;
    private final View view;

    public WalletController(WalletService walletService, View view) {
        this.walletService = walletService;
        this.view = view;
    }

    public void assign(UUID voucherId, String email) {
        walletService.assign(voucherId, email);
    }

    public void findAll(String email) {
        List<Voucher> vouchers = walletService.findAll(email);
        view.printVouchers(vouchers);
    }
}
