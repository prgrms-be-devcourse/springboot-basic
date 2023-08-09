package com.example.voucher.wallet.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ResponseStatus;
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
        try {
            WalletDTO wallet = walletService.createWallet(request.getCustomerId(), request.getVoucherId());

            return new WalletResponse(ResponseStatus.SC, wallet);
        } catch (Exception e) {
            return new WalletResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public WalletResponse getWalletByCustomer(WalletRequest walletRequest) {
        try {
            List<WalletDTO> wallets = walletService.getByCustomer(walletRequest.getCustomerId());

            return new WalletResponse(ResponseStatus.SC, wallets);
        } catch (Exception e) {
            return new WalletResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public WalletResponse getWalletByVoucher(WalletRequest walletRequest) {
        try {
            List<WalletDTO> wallets = walletService.getByVoucher(walletRequest.getVoucherId());

            return new WalletResponse(ResponseStatus.SC, wallets);
        } catch (Exception e) {
            return new WalletResponse(ResponseStatus.ER, e.getMessage());
        }
    }

    public WalletResponse deleteWallet(WalletRequest request) {
        try {
            walletService.deleteWallet(request.getCustomerId(), request.getVoucherId());

            return new WalletResponse(ResponseStatus.SC);
        } catch (Exception e) {
            return new WalletResponse(ResponseStatus.ER, e.getMessage());
        }
    }

}
