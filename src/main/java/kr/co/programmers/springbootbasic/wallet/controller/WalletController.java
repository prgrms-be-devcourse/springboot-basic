package kr.co.programmers.springbootbasic.wallet.controller;

import kr.co.programmers.springbootbasic.wallet.dto.WalletResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;
import kr.co.programmers.springbootbasic.wallet.service.WalletService;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public WalletSaveDto saveVoucherInWallet(WalletSaveDto saveRequest) {
        return walletService.saveVoucherInCustomerWallet(saveRequest);
    }

    public WalletResponse findWalletById(UUID walletId) {
        return walletService.findWalletById(walletId);
    }
}
