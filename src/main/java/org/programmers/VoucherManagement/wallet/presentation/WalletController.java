package org.programmers.VoucherManagement.wallet.presentation;

import org.programmers.VoucherManagement.wallet.application.WalletService;
import org.programmers.VoucherManagement.wallet.application.dto.WalletCreateRequest;
import org.programmers.VoucherManagement.wallet.application.dto.WalletGetResponses;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void createWallet(WalletCreateRequest walletCreateRequest) {
        walletService.createWallet(walletCreateRequest);
    }

    public WalletGetResponses getWalletsByVoucherId(String voucherId) {
        return walletService.getWalletsByVoucherId(voucherId);
    }

    public WalletGetResponses getWalletsByMemberId(String memberId) {
        return walletService.getWalletsByMemberId(memberId);
    }

    public void deleteWallet(UUID walletId) {
        walletService.deleteWallet(walletId);
    }
}
