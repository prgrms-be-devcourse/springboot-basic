package com.example.voucher.wallet.controller;

import java.util.List;
import org.springframework.stereotype.Controller;

import com.example.voucher.wallet.controller.model.WalletRequest;
import com.example.voucher.wallet.controller.model.WalletResponse;
import com.example.voucher.wallet.service.WalletService;
import com.example.voucher.wallet.service.dto.WalletDTO;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public WalletResponse createWallet(WalletRequest request) {
        WalletDTO wallet = walletService.createWallet(request.getCustomerId(), request.getVoucherId());

        return new WalletResponse(wallet);
    }

    public WalletResponse getWalletByCustomer(WalletRequest walletRequest) {
        List<WalletDTO> wallets = walletService.getByCustomer(walletRequest.getCustomerId());

        return new WalletResponse(wallets);
    }

    public WalletResponse getWalletByVoucher(WalletRequest walletRequest) {
        List<WalletDTO> wallets = walletService.getByVoucher(walletRequest.getVoucherId());

        return new WalletResponse(wallets);
    }

    public void deleteWallet(WalletRequest request) {
        walletService.deleteWallet(request.getCustomerId(), request.getVoucherId());
    }

}
