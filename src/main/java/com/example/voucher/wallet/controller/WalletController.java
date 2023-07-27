package com.example.voucher.wallet.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import com.example.voucher.request.WalletRequest;
import com.example.voucher.response.Response;
import com.example.voucher.wallet.service.dto.WalletDTO;
import com.example.voucher.wallet.service.WalletService;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public Response<WalletDTO> createWallet(WalletRequest.Create request) {
        WalletDTO wallet = walletService.createWallet(request.getCustomerId(), request.getVoucherId());

        return new Response<>(wallet);
    }

    public Response<WalletDTO> search(String condition, UUID conditionId) {
        List<WalletDTO> wallets = walletService.search(condition, conditionId);

        return new Response<>(wallets);
    }

    public void deleteWallet(UUID customerId, UUID voucherId) {
        walletService.deleteWallet(customerId, voucherId);
    }

}
