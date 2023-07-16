package org.programmers.VoucherManagement.wallet.presentation;

import org.programmers.VoucherManagement.wallet.application.WalletService;
import org.programmers.VoucherManagement.wallet.dto.CreateWalletRequest;
import org.programmers.VoucherManagement.wallet.dto.GetWalletListResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void createWallet(CreateWalletRequest createWalletRequest) {
        walletService.createWallet(createWalletRequest);
    }

    public GetWalletListResponse getWalletsByVoucherId(UUID voucherId) {
        return walletService.getWalletsByVoucherId(voucherId);
    }

    public GetWalletListResponse getWalletsByMemberId(UUID memberId) {
        return walletService.getWalletsByMemberId(memberId);
    }

    public void deleteWallet(UUID walletId) {
        walletService.deleteWallet(walletId);
    }
}
