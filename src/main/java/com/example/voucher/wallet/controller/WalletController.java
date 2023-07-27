package com.example.voucher.wallet.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import com.example.voucher.response.Response;
import com.example.voucher.wallet.service.WalletService;
import com.example.voucher.wallet.service.dto.WalletDTO;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public Response<WalletDTO> createWallet(WalletRequest request) {
        WalletDTO wallet = walletService.createWallet(request.getCustomerId(), request.getVoucherId());

        return new Response<>(wallet);
    }

    public Response<WalletDTO> getWalletByCustomer(WalletRequest walletRequest) {
        List<WalletDTO> wallets = walletService.getByCustomer(walletRequest.getCustomerId());

        return new Response<>(wallets);
    }

    public Response<WalletDTO> getWalletByVoucher(WalletRequest walletRequest) {
        List<WalletDTO> wallets = walletService.getByVoucher(walletRequest.getVoucherId());

        return new Response<>(wallets);
    }

    public void deleteWallet(WalletRequest request) {
        walletService.deleteWallet(request.getCustomerId(), request.getVoucherId());
    }

}
